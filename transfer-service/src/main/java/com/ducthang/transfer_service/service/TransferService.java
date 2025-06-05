package com.ducthang.transfer_service.service;

import com.ducthang.transfer_service.client.ClientAccountBankService;
import com.ducthang.transfer_service.dto.AccountBankDTO;
import com.ducthang.transfer_service.dto.EmailMessage;
import com.ducthang.transfer_service.entity.TransactionDetails;
import com.ducthang.transfer_service.repository.TransactionDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public AccountBankDTO transferMoney(String from, String to, String description, BigDecimal amount) {
        AccountBankDTO bank = clientAccountBankService.getAccountBank(from);
        if (bank == null) {
            throw new RuntimeException("user source not found");
        }

        AccountBankDTO accountBank = clientAccountBankService.getAccountBank(to);
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = clientAccountBankService.getUserName(from);
        if (!authenticationName.equals(username)) throw new RuntimeException("Invalid authentication");

        if (from.equals(to)) {
            throw new RuntimeException("duplicate from and to");
        }

        if (accountBank == null) {
            throw new RuntimeException("user destination not found");
        }

        if (bank.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("user balance is less than current balance");
        }

        accountBank.setBalance(accountBank.getBalance().add(amount));
        clientAccountBankService.updateAccountBankDTO(accountBank, to);

        bank.setBalance(bank.getBalance().subtract(amount));
        clientAccountBankService.updateAccountBankDTO(bank, from);

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setBankNumber(from);
        transactionDetails.setAmount(amount);
        String htmlTemplate = """
        <html>
        <head>
            <style>
                body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }
                .email-box {
                    max-width: 500px;
                    margin: auto;
                    padding: 20px;
                    background-color: white;
                    border: 1px solid #eee;
                    border-radius: 10px;
                    box-shadow: 0 0 10px rgba(0,0,0,0.05);
                }
                .amount { color: #2b7cff; font-weight: bold; }
                .account { font-weight: bold; }
                .footer { font-size: 12px; color: #666; margin-top: 20px; }
            </style>
        </head>
        <body>
            <div class="email-box">
                <h2>Thông báo chuyển tiền</h2>
                <p>Bạn vừa chuyển <span class="amount">%sđ</span> đến số tài khoản <span class="account">%s</span>.</p>
                <p><strong>Nội dung:</strong> %s</p>
                <div class="footer">
                    Đây là email tự động. Vui lòng không phản hồi.
                </div>
            </div>
        </body>
        </html>
        """;

        String desc = description.isEmpty()
                ? clientAccountBankService.getNameUser(from) + " chuyển tiền."
                : description;

        String htmlBody = String.format(htmlTemplate, amount, to, desc);



        EmailMessage message = new EmailMessage();
        message.setTo(clientAccountBankService.getEmail(from));
        message.setSubject("Thông báo chuyển tiền");
        message.setBody(htmlBody);

        rabbitTemplate.convertAndSend("email.topic.exchange", "email.routing.key", message); // UPDATED
        return bank;
    }



}
