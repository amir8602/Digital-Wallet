package ir.digipay.wallet.services;

import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import ir.digipay.wallet.models.wallet.entity.WalletEntity;

public interface WalletService {
    TransactionEntity withdraw(TransactionEntity transaction);

    WalletEntity saveWallet(WalletEntity walletEntity);

    WalletEntity updateWallet(WalletEntity walletEntity);

    void deleteWallet(Long id);

    TransactionEntity deposit(TransactionEntity transaction);

    TransactionEntity transfer(TransactionEntity transaction);
}
