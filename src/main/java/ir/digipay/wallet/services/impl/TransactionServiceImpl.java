package ir.digipay.wallet.services.impl;

import ir.digipay.wallet.exceptions.BaseException;
import ir.digipay.wallet.models.transaction.dao.TransactionDao;
import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import ir.digipay.wallet.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);


    @Autowired
    private TransactionDao transactionDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public TransactionEntity save(TransactionEntity transactionEntity) throws BaseException {
        return transactionDao.save(transactionEntity);
    }



}
