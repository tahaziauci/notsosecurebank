package com.example.Broken.Bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @NotEmpty(message = "Username should not be empty")
    @NotNull(message = "Username is mandatory")
    @Pattern(regexp="^[a-z0-9_\\-\\.]+$",message="username has illegal chars")
    @Size(min = 1, message="Username length should be between 1 - 127")
    @Size(max = 127, message="Username length should be between 1 - 127")
    String username;

    @NotEmpty(message = "Password should not be empty")
    @NotNull(message = "Password is mandatory")
    @Pattern(regexp="^[a-z0-9_\\-\\.]+$", message="password has illegal chars")
    @Size(min = 1, message="password length should be between 1 - 127")
    @Size(max = 127, message="password length should be between 1 - 127")
    String password;

    @NotNull(message = "Balance is mandatory")
    @DecimalMin(value= "0.0", message = "Initial balance should be bigger than 0.00")
    @DecimalMax(value= "4294967295.99", message = "Initial Balance should be smaller than 4294967295.99") // TODO: need to double check
    BigDecimal balance;

}
