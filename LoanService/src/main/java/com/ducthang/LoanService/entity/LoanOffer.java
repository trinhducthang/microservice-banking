package com.ducthang.LoanService.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document("LoanOffer")
public class LoanOffer {
    @Id
    private String offerId;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private String duration;
}
