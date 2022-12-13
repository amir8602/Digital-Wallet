package ir.digipay.wallet.services.impl;

import ir.digipay.wallet.WalletApplication;
import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import ir.digipay.wallet.models.transaction.entity.TransactionType;
import ir.digipay.wallet.models.user.entity.UserEntity;
import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import ir.digipay.wallet.models.wallet.entity.WalletStatus;
import ir.digipay.wallet.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WalletApplication.class)
class WalletServiceImplTest {

    @Autowired
    private WalletServiceImpl walletService;

    @Autowired
    TransactionService transactionService;

    @BeforeEach
    void setUp() {


        WalletEntity wallet = new WalletEntity();
        wallet.setId(1L);
        wallet.setName("one");
        wallet.setWalletStatus(WalletStatus.ACTIVE);
        wallet.setWalletNumber(122222L);
        wallet.setBalance(BigDecimal.valueOf(123000l));
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("amir");
        userEntity.setLastName("ghaderi");
        userEntity.setUsername("ghaderi");
        wallet.setUser(userEntity);
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(BigDecimal.valueOf(12l));
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setDestination(1000l);
        transaction.setSource(2000l);
        transaction.setWallet(wallet);
    }
    @Test
    void save () {
        TransactionEntity transaction=new TransactionEntity();
        transaction.setAmount(BigDecimal.valueOf(120l));
        TransactionEntity saveTransaction = transactionService.save(transaction);
        Long byId= transaction.getId();
        assertSame(saveTransaction.getId(), byId);

    }
}