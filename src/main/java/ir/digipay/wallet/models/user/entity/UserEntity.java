package ir.digipay.wallet.models.user.entity;

import ir.digipay.wallet.models.Audit;
import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class UserEntity extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID" , updatable = false , nullable = false)
    private Long id;

    @NotBlank(message = "username is required")
    @Size(max = 20)
    private String username;

    @Column(name = "FIRST_NAME")
    @NotBlank(message = "firstname is required")
    @Size(max = 20)
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotBlank(message = "lastname is required")
    @Size(max = 20)
    private String lastName;

    @OneToMany(mappedBy ="user")
    private List<WalletEntity> wallets = new ArrayList<>();

}
