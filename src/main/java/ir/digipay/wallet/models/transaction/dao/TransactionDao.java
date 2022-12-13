package ir.digipay.wallet.models.transaction.dao;

import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TransactionDao extends JpaRepository<TransactionEntity, Long> {
}
