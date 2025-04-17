package com.ducthang.LoanService.controller;

import com.ducthang.LoanService.dto.response.ApiResponse;
import com.ducthang.LoanService.entity.LoanOffer;
import com.ducthang.LoanService.service.LoanOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-offers")
@RequiredArgsConstructor
public class LoanOfferController {

    private final LoanOfferService loanOfferService;

    // GET ALL
    @GetMapping
    public Mono<ApiResponse<Object>> getAllLoanOffers() {
        return loanOfferService.getAllLoanOffers()
                .collectList()
                .map(offers -> ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Fetched successfully")
                        .result(offers)
                        .build())
                .onErrorResume(e -> Mono.just(ApiResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Error: " + e.getMessage())
                        .result(null)
                        .build()));
    }


    // GET BY ID
    @GetMapping("/{id}")
    public Mono<ApiResponse<Object>> getLoanOfferById(@PathVariable String id) {
        return loanOfferService.getLoanOfferById(id)
                .map(offer -> ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Loan offer found")
                        .result(offer)
                        .build())
                .switchIfEmpty(Mono.just(
                        ApiResponse.builder()
                                .code(HttpStatus.NOT_FOUND.value())
                                .message("Loan offer not found with ID: " + id)
                                .build()
                ))
                .onErrorResume(e -> {
                    // In Reactive, authentication exceptions are not typically caught here
                    return Mono.just(ApiResponse.builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Unexpected error: " + e.getMessage())
                            .build());
                });
    }




    // CREATE
    @PostMapping
    public Mono<ApiResponse<Object>> createLoanOffer(@RequestBody LoanOffer loanOffer) {
        try {
            LoanOffer created = loanOfferService.createLoanOffer(loanOffer);
            return Mono.just(ApiResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .message("Loan offer created")
                    .result(created)
                    .build());
        } catch (Exception e) {
            return Mono.just(ApiResponse.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Create loan offer failed: " + e.getMessage())
                    .build());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public Mono<ApiResponse<Object>> updateLoanOffer(@PathVariable String id, @RequestBody LoanOffer loanOffer) {
        return loanOfferService.updateLoanOffer(loanOffer, id)
                .map(updated -> ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Loan offer updated")
                        .result(updated)
                        .build())
                .switchIfEmpty(Mono.just(ApiResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message("Loan offer not found with ID: " + id)
                        .build()))
                .onErrorResume(e -> Mono.just(ApiResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Update failed: " + e.getMessage())
                        .build()));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Object>> deleteLoanOffer(@PathVariable String id) {
        return loanOfferService.deleteLoanOffer(id)
                .map(success -> ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Loan offer deleted")
                        .result(success)
                        .build())
                .onErrorResume(e -> Mono.just(ApiResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Delete failed: " + e.getMessage())
                        .result(false)
                        .build()));
    }
}
