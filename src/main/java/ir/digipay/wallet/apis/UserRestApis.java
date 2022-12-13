package ir.digipay.wallet.apis;

import ir.digipay.wallet.exceptions.BaseException;
import ir.digipay.wallet.mappers.UserMapper;
import ir.digipay.wallet.models.ValidGroup;
import ir.digipay.wallet.models.user.dto.UserDto;
import ir.digipay.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/")
public class UserRestApis {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/find")
    @PreAuthorize("hasRole('USER')")
    public UserDto find()throws BaseException {
        return userMapper.entityToDto(userService.find());
    }


    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public UserDto updateUser(@RequestBody @Validated(ValidGroup.class) UserDto userDto) throws BaseException{
        return  userMapper.entityToDto(userService.updateUser(userMapper.dtoToEntity(userDto)));
    }

}
