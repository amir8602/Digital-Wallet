package ir.digipay.wallet.apis;

import ir.digipay.wallet.exceptions.BaseException;
import ir.digipay.wallet.mappers.TransactionMapper;
import ir.digipay.wallet.mappers.WalletMapper;
import ir.digipay.wallet.models.ValidGroup;
import ir.digipay.wallet.models.transaction.dto.TransactionDto;
import ir.digipay.wallet.models.wallet.dto.WalletDto;
import ir.digipay.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets/")
public class WalletRestApis {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletMapper walletMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public WalletDto saveWallet(@RequestBody @Validated(ValidGroup.class) WalletDto walletDto) throws BaseException {
        return walletMapper.entityToDto(walletService.saveWallet(walletMapper.dtoToEntity(walletDto)));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public void deleteWallet(@Validated(ValidGroup.class) @PathVariable("id") Long id) throws BaseException{
        walletService.deleteWallet(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public WalletDto updateWallet(@Validated(ValidGroup.class) WalletDto walletDto) throws BaseException{

        return walletMapper.entityToDto(walletService.updateWallet(walletMapper.dtoToEntity(walletDto)));
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('USER')")
    public TransactionDto deposit(@RequestBody @Validated(ValidGroup.class) TransactionDto transactionDto) throws BaseException {
        return transactionMapper.entityToDto(walletService.deposit(transactionMapper.dtoToEntity(transactionDto)));
    }


    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('USER')")
    public TransactionDto withdraw(@RequestBody @Validated(ValidGroup.class) TransactionDto transactionDto) throws BaseException {
        return transactionMapper.entityToDto(walletService.withdraw(transactionMapper.dtoToEntity(transactionDto)));
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USER')")
    public TransactionDto transfer(@RequestBody @Validated(ValidGroup.class) TransactionDto transactionDto) throws BaseException {
        return transactionMapper.entityToDto(walletService.transfer(transactionMapper.dtoToEntity(transactionDto)));
    }

}
