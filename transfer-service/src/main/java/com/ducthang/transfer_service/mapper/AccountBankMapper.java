package com.ducthang.transfer_service.mapper;

import com.ducthang.transfer_service.dto.AccountBankDTO;
import com.ducthang.transfer_service.entity.AccountBank;
import org.springframework.stereotype.Component;

@Component
public class AccountBankMapper {

    public AccountBankDTO toDTO(AccountBank accountBank) {
        if ( accountBank == null ) {
            return null;
        }

        AccountBankDTO accountBankDTO = new AccountBankDTO();

        accountBankDTO.setAccountName( accountBank.getAccountName() );
        accountBankDTO.setAccountNumber( accountBank.getAccountNumber() );
        accountBankDTO.setBalance( accountBank.getBalance() );

        return accountBankDTO;
    }
}
