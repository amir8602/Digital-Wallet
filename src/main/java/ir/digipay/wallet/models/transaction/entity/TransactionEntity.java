package ir.digipay.wallet.models.transaction.entity;

import ir.digipay.wallet.models.ValidGroup;
import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "TRANSACTION")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;


    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    private WalletEntity wallet;

    @Column(name = "AMOUNT")
    @NotEmpty(groups = ValidGroup.class , message = "amount required")
    @Range(min = 1 , groups = ValidGroup.class , message = "amount should be greater than 0")
    private BigDecimal amount;

    @NotEmpty(groups = ValidGroup.class)
    private Long source;

    @NotEmpty(groups = ValidGroup.class)
    private Long destination;

}
