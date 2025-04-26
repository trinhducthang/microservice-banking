package com.ducthang.transfer_service.controller;

import com.ducthang.transfer_service.dto.ApiResponse;
import com.ducthang.transfer_service.entity.TransactionDetails;
import com.ducthang.transfer_service.repository.TransactionDetailsRepository;
import com.ducthang.transfer_service.service.TransactionDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TransactionDetailsController {

    private final TransactionDetailsService transactionDetailsService;
    private final TransactionDetailsRepository transactionDetailsRepository;

    @GetMapping("/transactions/stats")
    public ApiResponse<Map<String, Object>> getTransactionStatsByDateAndBankNumber(
            @RequestParam("date") String date,
            @RequestParam("bankNumber") String bankNumber) {

        LocalDate transactionDate = LocalDate.parse(date);
        long transactionCount = transactionDetailsService.getTransactionCountByDateAndBankNumber(transactionDate, bankNumber);
        BigDecimal transactionSum = transactionDetailsService.getTransactionSumByDateAndBankNumber(transactionDate, bankNumber);

        Map<String, Object> result = new HashMap<>();
        result.put("transactionCount", transactionCount);
        result.put("transactionSum", transactionSum);

        return ApiResponse.<Map<String, Object>>builder()
                .code(HttpStatus.OK.value())
                .message("Get transaction statistics success!")
                .result(result)
                .build();
    }


    @GetMapping("/bank-summary")
    public ApiResponse<List<TransactionDetailsRepository.BankTransactionSummary>> getBankTransactionSummary() {
        List<TransactionDetailsRepository.BankTransactionSummary> summary = transactionDetailsService.getTransactionSummaryByBankNumber();
        return ApiResponse.<List<TransactionDetailsRepository.BankTransactionSummary>>builder()
                .code(HttpStatus.OK.value())
                .message("Get bank transaction summary success!")
                .result(summary)
                .build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/transactions")
    public Page<TransactionDetails> getTransactionDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // Tạo đối tượng Pageable với thông tin page và size
        Pageable pageable = PageRequest.of(page, size);

        // Lấy dữ liệu phân trang từ repository
        return transactionDetailsRepository.findAll(pageable);
    }

    @GetMapping("/monthly-summary")
    public List<Object[]> getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {
        return transactionDetailsService.getMonthlyTransactionSummary(year, month);
    }

}
