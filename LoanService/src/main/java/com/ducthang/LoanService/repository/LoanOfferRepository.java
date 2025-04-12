package com.ducthang.LoanService.repository;

import com.ducthang.LoanService.entity.LoanOffer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LoanOfferRepository extends ReactiveMongoRepository<LoanOffer, String> {

}
