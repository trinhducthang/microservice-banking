package com.ducthang.transfer_service.client;

import com.ducthang.transfer_service.dto.AccountBankDTO;
import com.ducthang.transfer_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ClientAccountBankService {
    private final WebClient webClient;

    public AccountBankDTO getAccountBank(String id) {
        return webClient.get()
                .uri("/abcbank/bank/getBank/{id}", id)
                .retrieve()
                .bodyToMono(AccountBankDTO.class)
                .block(); // <- BLOCKING để chờ kết quả
    }

    public AccountBankDTO updateAccountBankDTO(AccountBankDTO accountBankDTO, String number) {
        return webClient.put()
                .uri("/abcbank/bank/getBank/{number}", number)
                .bodyValue(accountBankDTO)
                .retrieve()
                .bodyToMono(AccountBankDTO.class)
                .block(); // Blocking chỉ dùng khi cần đồng bộ
    }

    public String getNameUser(String accountNumber) {
        return webClient.get()
                .uri("/abcbank/bank/getUser/{accountNumber}",accountNumber)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    public String getUserName(String accountNumber) {
        return webClient.get()
                .uri("/abcbank/bank/getUserName/{accountNumber}",accountNumber)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    public String getEmail(String accountNumber) {
        return webClient.get()
                .uri("/abcbank/bank/getEmail/{accountNumber}",accountNumber)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
