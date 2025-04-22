package com.ducthang.transfer_service.service;

import com.ducthang.transfer_service.client.ClientAccountBankService;
import com.ducthang.transfer_service.dto.AccountBankDTO;
import com.ducthang.transfer_service.entity.User;
import com.ducthang.transfer_service.mapper.AccountBankMapper;
import com.ducthang.transfer_service.entity.AccountBank;
import com.ducthang.transfer_service.entity.TransactionDetails;
import com.ducthang.transfer_service.repository.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransactionDetailsRepository transactionDetailsRepository;

    private final ClientAccountBankService clientAccountBankService;

    private final AccountBankMapper accountBankMapper;


    public AccountBankDTO transferMoney(String from, String to, String description, BigDecimal amount) {


        AccountBankDTO bank = clientAccountBankService.getAccountBank(from);
        if (bank == null) {
            throw new RuntimeException("user source not found");
        }
        AccountBankDTO accountBank = clientAccountBankService.getAccountBank(to);
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = clientAccountBankService.getUserName(from);
        if(!authenticationName.equals(username)) throw new RuntimeException("Invalid authentication");
        if(from.equals(to)) {
            throw new RuntimeException("duplicate from and to");
        }
        if (accountBank == null) {
            throw new RuntimeException("user destination not found");
        }
        if (bank.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("user balance is less than current balance");
        }
        accountBank.setBalance(accountBank.getBalance().add(amount));
        clientAccountBankService.updateAccountBankDTO(accountBank,to);
        bank.setBalance(bank.getBalance().subtract(amount));
        clientAccountBankService.updateAccountBankDTO(bank,from);
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setBankNumber(from);
        transactionDetails.setAmount(amount);
        if(description.isEmpty()){
            transactionDetails.setDescription(clientAccountBankService.getNameUser(from) + " chuyen tien.");
        }
        else{
            transactionDetails.setDescription(description);
        }
        transactionDetails.setTransactionDate(LocalDate.now());
        transactionDetailsRepository.save(transactionDetails);
        return bank;
    }
}
