package ir.digipay.wallet.services.impl;

import ir.digipay.wallet.exceptions.BaseException;
import ir.digipay.wallet.models.user.dao.UserDao;
import ir.digipay.wallet.models.user.entity.UserEntity;
import ir.digipay.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserEntity saveUser(UserEntity userEntity) throws BaseException {
        return userDao.save(userEntity);
    }

    @Override
    @Transactional
    public UserEntity findUserByUsername(String username) throws BaseException {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserEntity userEntity) throws BaseException {
        return userDao.save(userEntity);
    }


    @Override
    @Transactional
    public UserEntity find() throws BaseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userDao.findByUsername(username);
    }



}
