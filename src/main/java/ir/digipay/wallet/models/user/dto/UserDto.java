package ir.digipay.wallet.models.user.dto;

import ir.digipay.wallet.models.wallet.entity.WalletEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "username is required")
    @Size(max = 20)
    private String username;

    @NotBlank(message = "firstname is required")
    @Size(max = 20)
    private String firstName;

    @NotBlank(message = "lastname is required")
    @Size(max = 20)
    private String lastName;

    private List<WalletEntity> wallets;

}
