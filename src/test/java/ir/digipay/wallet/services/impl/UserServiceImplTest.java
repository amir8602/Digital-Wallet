package ir.digipay.wallet.services.impl;

import ir.digipay.wallet.WalletApplication;
import ir.digipay.wallet.models.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WalletApplication.class)
@EnableTransactionManagement

class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {



        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("amir");
        userEntity.setLastName("ghaderi");
        userEntity.setUsername("ghaderi");


    }
    @Test
    void saveUser() {
        UserEntity userEntity=new UserEntity();
        userEntity.setFirstName("amir");
        UserEntity saveUser = userService.saveUser(userEntity);
        Long id = userEntity.getId();
        assertSame(saveUser.getId(), id);
    }
}