package ir.digipay.wallet.services;

import ir.digipay.wallet.models.user.entity.UserEntity;

public interface UserService {
    UserEntity saveUser(UserEntity userEntity);

    UserEntity findUserByUsername(String username);

    UserEntity updateUser(UserEntity userEntity);

    UserEntity find();
}
