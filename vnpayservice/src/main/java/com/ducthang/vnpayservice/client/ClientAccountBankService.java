package com.ducthang.vnpayservice.client;

import com.ducthang.vnpayservice.entity.AccountBankDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ClientAccountBankService {
    private final WebClient webClient;

    public AccountBankDTO getAccountBankDTO(String id) {
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

}
