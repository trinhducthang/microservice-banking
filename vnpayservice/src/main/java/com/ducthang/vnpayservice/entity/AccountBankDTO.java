package com.ducthang.vnpayservice.entity;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBankDTO {


    private Long userId;

    private AccountName accountName;

    private String accountNumber;

    private BigDecimal balance;
}
