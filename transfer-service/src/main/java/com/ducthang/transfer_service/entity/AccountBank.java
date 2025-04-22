package com.ducthang.transfer_service.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class AccountBank {

    private Long id;

    private User user;

    private String accountNumber;

    private BigDecimal balance = BigDecimal.ZERO;

    private AccountName accountName;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
