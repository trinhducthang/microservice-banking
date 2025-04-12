package com.ducthang.LoanService.service.impl;

import com.ducthang.LoanService.entity.LoanOffer;
import com.ducthang.LoanService.repository.LoanOfferRepository;
import com.ducthang.LoanService.service.LoanOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoanOfferServiceImpl implements LoanOfferService {
    private final LoanOfferRepository loanOfferRepository;


    @Override
    public Flux<LoanOffer> getAllLoanOffers() {
        return loanOfferRepository.findAll();
    }

    @Override
    public Mono<LoanOffer> getLoanOfferById(String id) {
        return loanOfferRepository.findById(id);
    }

    @Override
    public Mono<LoanOffer> createLoanOffer(LoanOffer loanOffer) {
        return loanOfferRepository.save(loanOffer);
    }

    @Override
    public Mono<LoanOffer> updateLoanOffer(LoanOffer loanOffer, String loanOfferId) {
        return loanOfferRepository.findById(loanOfferId)
                .flatMap(existingOffer -> {
                    existingOffer.setLoanAmount(loanOffer.getLoanAmount());
                    existingOffer.setDuration(loanOffer.getDuration());
                    existingOffer.setInterestRate(loanOffer.getInterestRate());
                    return loanOfferRepository.save(existingOffer);
                });
    }

}
