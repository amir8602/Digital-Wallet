package ir.digipay.wallet.models.user.dao;

import ir.digipay.wallet.models.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity , Long> {

    UserEntity findByUsername (String userName);

}
