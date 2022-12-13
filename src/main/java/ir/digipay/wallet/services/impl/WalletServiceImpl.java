package ir.digipay.wallet.services.impl;

import ir.digipay.wallet.exceptions.BaseException;
import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import ir.digipay.wallet.models.transaction.entity.TransactionStatus;
import ir.digipay.wallet.models.transaction.entity.TransactionType;
import ir.digipay.wallet.models.user.entity.UserEntity;
import ir.digipay.wallet.models.wallet.dao.WalletDao;
import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import ir.digipay.wallet.models.wallet.entity.WalletStatus;
import ir.digipay.wallet.services.TransactionService;
import ir.digipay.wallet.services.UserService;
import ir.digipay.wallet.services.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;


@Service
public class WalletServiceImpl implements WalletService {

    Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    private WalletDao walletDao;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;


    private UserEntity findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findUserByUsername(username);
    }

    @Override
    @Transactional
    public WalletEntity saveWallet(WalletEntity walletEntity) throws BaseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userByUsername = userService.findUserByUsername(username);
        SecureRandom random = new SecureRandom();
        long l = random.nextLong();
        if (l < 0) {
            l = l * (-1);
        }
        walletEntity.setUser(userByUsername);
        walletEntity.setWalletNumber(l);
        walletEntity.setWalletStatus(WalletStatus.ACTIVE);
        walletEntity.setBalance(BigDecimal.valueOf(0));
        return walletDao.save(walletEntity);
    }

    @Override
    @Transactional
    public WalletEntity updateWallet(WalletEntity walletEntity) {
        return walletDao.save(walletEntity);
    }

    @Override
    public void deleteWallet(Long id) {
        walletDao.deleteById(id);
    }


    @Transactional
    @Override
    public TransactionEntity withdraw(TransactionEntity transaction) throws BaseException {
        UserEntity loginUser = findUser();
        WalletEntity sourceWallet = walletDao.findByWalletNumber(transaction.getSource());
        if (sourceWallet == null) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.WITHDRAW);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Wallet Not Found", "Transaction Error");
        }
        if (sourceWallet.getWalletStatus().equals(WalletStatus.NOTACTIVE)) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.WITHDRAW);
            transaction.setWallet(sourceWallet);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Wallet Not Active", "Transaction Error");
        }

        if (!(loginUser.getId().equals(sourceWallet.getUser().getId()))) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.WITHDRAW);
            transaction.setWallet(sourceWallet);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("This wallet is not for you", "Transaction Error");
        }


        if (sourceWallet.getBalance().compareTo(transaction.getAmount()) < 0) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.WITHDRAW);
            transaction.setWallet(sourceWallet);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Balance Is Lower Than Amount", "Transaction Error");
        }


        BigDecimal balance = sourceWallet.getBalance();
        sourceWallet.setBalance(balance.subtract(transaction.getAmount()));
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setWallet(sourceWallet);
        TransactionEntity save = transactionService.save(transaction);
        LOGGER.info(save.toString());
        walletDao.save(sourceWallet);
        return save;
    }


    @Transactional
    @Override
    public TransactionEntity deposit(TransactionEntity transaction) throws BaseException {

        UserEntity loginUser = findUser();

        WalletEntity targetWallet = walletDao.findByWalletNumber(transaction.getDestination());

        if (targetWallet == null) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.WITHDRAW);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Wallet Not Found", "Transaction Error");
        }

        if (targetWallet.getWalletStatus().equals(WalletStatus.NOTACTIVE)) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.WITHDRAW);
            transaction.setWallet(targetWallet);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Wallet Not Active", "Transaction Error");
        }

        if (!(loginUser.getId().equals(targetWallet.getUser().getId()))) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.WITHDRAW);
            transaction.setWallet(targetWallet);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("This wallet is not for you", "Transaction Error");
        }


        BigDecimal balance = targetWallet.getBalance();
        targetWallet.setBalance(balance.add(transaction.getAmount()));
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setWallet(targetWallet);
        TransactionEntity save = transactionService.save(transaction);
        walletDao.save(targetWallet);
        return save;
    }

    @Transactional
    @Override
    public TransactionEntity transfer(TransactionEntity transaction) {

        UserEntity loginUser = findUser();

        WalletEntity first = walletDao.findByWalletNumber(transaction.getSource());
        WalletEntity second = walletDao.findByWalletNumber(transaction.getDestination());
        if (first == null || second == null) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.TRANSFER);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Wallets Not Found", "Transaction Error");
        }
        if (first.getWalletStatus().equals(WalletStatus.NOTACTIVE) || second.getWalletStatus().equals(WalletStatus.NOTACTIVE)) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.TRANSFER);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Wallets Not Active", "Transaction Error");
        }
        if (!(first.getUser().getId().equals(loginUser.getId()))) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.TRANSFER);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Source wallet is not for you", "Transaction Error");
        }
        if (first.getBalance().compareTo(transaction.getAmount()) < 0) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transaction.setTransactionType(TransactionType.TRANSFER);
            TransactionEntity save = transactionService.save(transaction);
            LOGGER.info(save.toString());
            throw new BaseException("Balance Is Lower Than Amount", "Transaction Error");
        }

        first.setBalance(first.getBalance().subtract(transaction.getAmount()));
        second.setBalance(second.getBalance().add(transaction.getAmount()));
        walletDao.save(first);
        walletDao.save(second);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        TransactionEntity save = transactionService.save(transaction);
        return save;


    }



}
