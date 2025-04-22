package com.ducthang.transfer_service.controller;

import com.ducthang.transfer_service.dto.AccountBankDTO;
import com.ducthang.transfer_service.dto.ApiResponse;
import com.ducthang.transfer_service.dto.TransferDTO;
import com.ducthang.transfer_service.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ApiResponse<AccountBankDTO> transfer(@RequestBody TransferDTO transferDTO) {
        try {
            return ApiResponse.<AccountBankDTO>builder()
                    .code(HttpStatus.OK.value())
                    .message("Transfer success")
                    .result(transferService.transferMoney(transferDTO.from,transferDTO.to,transferDTO.description,transferDTO.amount))
                    .build();
        }
        catch (Exception e) {
            return ApiResponse.<AccountBankDTO>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }
}
