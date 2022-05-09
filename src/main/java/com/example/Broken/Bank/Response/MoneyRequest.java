package com.example.Broken.Bank.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyRequest {

    @NotEmpty(message = "Username should not be empty")
    @NotNull(message = "Username is mandatory")
    @Pattern(regexp = "^[a-z0-9_\\-\\.]+$", message = "username has illegal chars")
    @Size(min = 1, message = "Username length should be between 1 - 127")
    @Size(max = 127, message = "Username length should be between 1 - 127")
    String username;

    @NotNull(message = "Withdraw/Deposit amount is mandatory")
    @Digits(integer= 10, fraction = 2, message = "Amount format is incorrect")
    @DecimalMin(value= "0.0", message = "Amount must be a positive number")
    @DecimalMax(value= "4294967295.99", message = "You got a lot of money to operate! Fail!") // TODO: need to double check
    BigDecimal amount;
}
