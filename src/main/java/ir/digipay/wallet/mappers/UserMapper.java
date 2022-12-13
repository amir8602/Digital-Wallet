package ir.digipay.wallet.mappers;

import ir.digipay.wallet.models.user.dto.UserDto;
import ir.digipay.wallet.models.user.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity dtoToEntity(UserDto userDto);

    UserDto entityToDto(UserEntity userEntity);

    List<UserEntity> dtoListToEntityList(List<UserDto> userDtos);

    List<UserDto> entityListToDtoList(List<UserEntity> userEntities);

}
