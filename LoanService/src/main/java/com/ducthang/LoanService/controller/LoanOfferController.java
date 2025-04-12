package com.ducthang.LoanService.controller;

import com.ducthang.LoanService.entity.LoanOffer;
import com.ducthang.LoanService.service.LoanOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/loanOffers")
@RequiredArgsConstructor
public class LoanOfferController {
    private final LoanOfferService loanOfferService;

    @GetMapping
    public Flux<LoanOffer> getAllLoanOffers() {
        return loanOfferService.getAllLoanOffers();
    }

    @PostMapping
    public Mono<LoanOffer> createLoanOffer(@RequestBody LoanOffer loanOffer) {
        return loanOfferService.createLoanOffer(loanOffer);
    }

    @GetMapping("/{id}")
    public Mono<LoanOffer> getLoanOfferById(@PathVariable String id) {
        return loanOfferService.getLoanOfferById(id);
    }

    @PutMapping("/{id}")
    public Mono<LoanOffer> updateLoanOffer(@RequestBody LoanOffer loanOffer, @PathVariable String id) {
        return loanOfferService.updateLoanOffer(loanOffer, id);
    }
}
