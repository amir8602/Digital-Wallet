package ir.digipay.wallet.models.wallet.dao;

import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WalletDao extends JpaRepository<WalletEntity , Long> {

    WalletEntity findByWalletNumber(Long walletNumber);

}
