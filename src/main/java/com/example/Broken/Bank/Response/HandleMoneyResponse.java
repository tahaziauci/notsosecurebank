package com.example.Broken.Bank.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandleMoneyResponse {
    private int code;
    private Date timestamp;
    private String message;
    private String username;
    private BigDecimal balance;
}