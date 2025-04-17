package com.ducthang.vnpayservice.payment;


import com.ducthang.vnpayservice.entity.VNPayUtil;
import com.ducthang.vnpayservice.client.ClientAccountBankService;
import com.ducthang.vnpayservice.configuration.VNPAYConfig;
import com.ducthang.vnpayservice.entity.AccountBankDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;

    public static String bankNumber_static;

    public static BigDecimal amount_static;

    private final ClientAccountBankService clientAccountBankService;



    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        amount_static = BigDecimal.valueOf(amount / 100);
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        String bankNumber = request.getParameter("bankNumber");
        vnpParamsMap.put("vnp_OrderInfo",bankNumber);
        bankNumber_static = bankNumber;
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    public PaymentDTO.VNPayResponse payCallbackHandler(HttpServletRequest request) {
        AccountBankDTO accountBankDTO = clientAccountBankService.getAccountBankDTO(bankNumber_static);
        BigDecimal amount = accountBankDTO.getBalance();
        BigDecimal amountDestination = amount.add(amount_static);
        accountBankDTO.setBalance(amountDestination);
        clientAccountBankService.updateAccountBankDTO(accountBankDTO,bankNumber_static);
//        AccountBank bank = accountRepository.findByAccountNumber(bankNumber_static);
//        BigDecimal amount = bank.getBalance();
//        BigDecimal amountDestination = amount.add(amount_static);
//        bank.setBalance(amountDestination);
//        accountRepository.save(bank);
        return new PaymentDTO.VNPayResponse(String.valueOf(HttpStatus.OK.value()),"success","OK");
    }



}
