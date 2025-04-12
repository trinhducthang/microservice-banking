package com.ducthang.LoanService.service;

import com.ducthang.LoanService.entity.LoanOffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface LoanOfferService {
    public Flux<LoanOffer> getAllLoanOffers();
    public Mono<LoanOffer> getLoanOfferById(String  id);
    public Mono<LoanOffer> createLoanOffer(LoanOffer loanOffer);
    public Mono<LoanOffer> updateLoanOffer(LoanOffer loanOffer, String loanOfferId);
}
