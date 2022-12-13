package ir.digipay.wallet.mappers;

import ir.digipay.wallet.models.transaction.dto.TransactionDto;
import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import ir.digipay.wallet.models.wallet.dto.WalletDto;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionEntity dtoToEntity(TransactionDto transactionDto);

    TransactionDto entityToDto(TransactionEntity TransactionDto);

    List<TransactionEntity> dtoListToEntityList(List<TransactionDto> transactionDtos);

    List<TransactionDto> entityListToDtoList(List<TransactionEntity> transactionEntities);
}
