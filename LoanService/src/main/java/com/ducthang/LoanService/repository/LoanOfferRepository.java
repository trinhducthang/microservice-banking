package com.ducthang.LoanService.repository;

import com.ducthang.LoanService.entity.LoanOffer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface LoanOfferRepository extends ReactiveMongoRepository<LoanOffer, String> {
    Mono<Boolean> existsAllByDurationAndInterestRateAndLoanAmount(String duration, BigDecimal interestRate, BigDecimal loanAmount);
}
