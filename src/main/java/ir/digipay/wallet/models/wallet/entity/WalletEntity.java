package ir.digipay.wallet.models.wallet.entity;

import ir.digipay.wallet.models.Audit;
import ir.digipay.wallet.models.transaction.entity.TransactionEntity;
import ir.digipay.wallet.models.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WALLET")
public class WalletEntity extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID" ,updatable = false , nullable = false)
    private Long id;

    @Column(name = "WALLET_NUMBER" , unique = true)
    private Long walletNumber;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private WalletStatus walletStatus;

    @ManyToOne
    private UserEntity user;

    @NotBlank(message = "name required")
    private String name;

    @OneToMany(mappedBy = "wallet")
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

}
