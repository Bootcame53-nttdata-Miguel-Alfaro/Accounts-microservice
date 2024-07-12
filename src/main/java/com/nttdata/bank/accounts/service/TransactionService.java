package com.nttdata.bank.accounts.service;

import com.nttdata.bank.accounts.domain.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<Transaction> deposit(String accountId, Operation operation);
    Mono<Transaction> withdraw(String accountId, Operation operation);
    Flux<Transaction> getTransactions(String accountId);
    Flux<DailyBalanceSummary> generateDailyBalanceSummary(String customerId);
    Mono<ValidateResponse> validateBalance(String accountId, Mono<ValidateRequest> operation);
}
