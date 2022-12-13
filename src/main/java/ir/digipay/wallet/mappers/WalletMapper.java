package ir.digipay.wallet.mappers;

import ir.digipay.wallet.models.user.dto.UserDto;
import ir.digipay.wallet.models.user.entity.UserEntity;
import ir.digipay.wallet.models.wallet.dto.WalletDto;
import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletEntity dtoToEntity(WalletDto walletDto);

    WalletDto entityToDto(WalletEntity walletEntity);

    List<WalletEntity> dtoListToEntityList(List<WalletDto> walletDtos);

    List<WalletDto> entityListToDtoList(List<WalletEntity> walletEntities);

}
