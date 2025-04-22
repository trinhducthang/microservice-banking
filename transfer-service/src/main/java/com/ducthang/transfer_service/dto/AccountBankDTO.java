package com.ducthang.transfer_service.dto;


import com.ducthang.transfer_service.entity.AccountName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBankDTO {

    private Long userId;

    private AccountName accountName;

    private String accountNumber;


    private BigDecimal balance;
}
