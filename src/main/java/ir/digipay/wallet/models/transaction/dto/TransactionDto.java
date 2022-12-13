package ir.digipay.wallet.models.transaction.dto;

import ir.digipay.wallet.models.ValidGroup;
import ir.digipay.wallet.models.transaction.entity.TransactionStatus;
import ir.digipay.wallet.models.transaction.entity.TransactionType;
import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {


    private Long id;

    private Date createDate;

    private TransactionStatus transactionStatus;

    private TransactionType transactionType;


    @NotEmpty(groups = ValidGroup.class ,  message = "amount required")
    @Range(min = 1 , groups = ValidGroup.class , message = "amount should be greater than 0")
    private BigDecimal amount;

    @NotEmpty(groups = ValidGroup.class)
    private Long source;

    @NotEmpty(groups = ValidGroup.class)
    private Long destination;

}
