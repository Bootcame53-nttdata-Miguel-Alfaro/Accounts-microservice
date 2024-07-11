package com.nttdata.bank.accounts.service;

import reactor.core.publisher.Mono;

public interface CustomerValidationService {
    Mono<Boolean> validateCustomerExists(String customerId);
}
