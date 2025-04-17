package com.ducthang.vnpayservice.controller;

import com.ducthang.vnpayservice.client.ClientAccountBankService;
import com.ducthang.vnpayservice.entity.AccountBankDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vn-pay")
@RequiredArgsConstructor
public class VNPayController {
    private final ClientAccountBankService clientAccountBankService;

    @GetMapping("/{accountNumber}")
    public AccountBankDTO getAccountBank(@PathVariable String accountNumber) {
        return clientAccountBankService.getAccountBankDTO(accountNumber);
    }

    @PutMapping("/{accountNumber}")
    public AccountBankDTO updateAccountBank(@PathVariable String accountNumber, @RequestBody AccountBankDTO accountBankDTO) {
        return clientAccountBankService.updateAccountBankDTO(accountBankDTO, accountNumber);
    }
}
