package com.ducthang.transfer_service.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table
@Entity
@Data
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bankNumber;

    private BigDecimal amount;

    private String description;

    private LocalDate transactionDate;
}
