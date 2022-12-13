package ir.digipay.wallet.models.wallet.dto;

import ir.digipay.wallet.models.ValidGroup;
import ir.digipay.wallet.models.transaction.dto.TransactionDto;
import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import ir.digipay.wallet.models.user.entity.UserEntity;
import ir.digipay.wallet.models.wallet.entity.WalletStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private Long id;

    private Long walletNumber;

    private BigDecimal balance;

    private WalletStatus walletStatus;

    @NotBlank(message = "name required" , groups = ValidGroup.class)
    private String name;

    private List<TransactionDto> transactionDtos = new ArrayList<>();

}

