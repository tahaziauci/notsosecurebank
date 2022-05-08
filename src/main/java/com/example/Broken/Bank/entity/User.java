package com.example.Broken.Bank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty(message = "Username should not be empty")
    @NotNull(message = "Username is mandatory")
    @Pattern(regexp = "^[a-z0-9_\\-\\.]+$", message = "username has illegal chars")
    @Size(min = 1, message = "Username length should be between 1 - 127")
    @Size(max = 127, message = "Username length should be between 1 - 127")
    String username;

    @NotEmpty(message = "Password should not be empty")
    @NotNull(message = "Password is mandatory")
    @Pattern(regexp = "^[a-z0-9_\\-\\.]+$", message = "password has illegal chars")
    @Size(min = 1, message = "password length should be between 1 - 127")
    @Size(max = 127, message = "password length should be between 1 - 127")
    String password;

    BigDecimal balance;
}