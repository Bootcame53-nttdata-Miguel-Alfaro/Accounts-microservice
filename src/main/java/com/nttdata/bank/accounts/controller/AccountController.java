package com.nttdata.bank.accounts.controller;

import com.nttdata.bank.accounts.api.AccountsApi;
import com.nttdata.bank.accounts.facade.AccountServiceFacade;
import com.nttdata.bank.accounts.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController

public class AccountController implements AccountsApi {

    private final AccountServiceFacade serviceFacade;

    public AccountController(AccountServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @Override
    public Mono<ResponseEntity<Account>> addAccount(Mono<Account> account, ServerWebExchange exchange) {
        return serviceFacade.getAccountService().save(account.map(serviceFacade.getAccountMapper()::toDomain))
                .map(serviceFacade.getAccountMapper()::toModel)
                .map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c));
    }

    @Override
    public Mono<ResponseEntity<Account>> getAccountById(String id, ServerWebExchange exchange) {
        return serviceFacade.getAccountService().findById(id)
                .map(serviceFacade.getAccountMapper()::toModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Account>> updateAccount(String id, Mono<Account> account, ServerWebExchange exchange) {
        return serviceFacade.getAccountService().update(id, account.map(serviceFacade.getAccountMapper()::toDomain))
                .map(serviceFacade.getAccountMapper()::toModel)
                .map(c ->ResponseEntity.status(HttpStatus.OK).body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(String id, ServerWebExchange exchange) {
        return serviceFacade.getAccountService().findById(id)
                .flatMap(c -> serviceFacade.getAccountService().delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<GetAccountBalance200Response>> getAccountBalance(String id, ServerWebExchange exchange) {
        return serviceFacade.getAccountService().getBalance(id)
                .map(serviceFacade.getBalanceMapper()::toModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Transaction>> depositToAccount(String id, Mono<DepositToAccountRequest> depositToAccountRequest, ServerWebExchange exchange) {
        return depositToAccountRequest
                .map(serviceFacade.getDepositMapper()::toDomain)
                .flatMap(deposit -> serviceFacade.getTransactionService().deposit(id, deposit))
                .map(serviceFacade.getTransactionMapper()::toModel)
                .map(transaction -> ResponseEntity.status(HttpStatus.OK).body(transaction));
    }

    @Override
    public Mono<ResponseEntity<Transaction>> withdrawFromAccount(String id, Mono<WithdrawFromAccountRequest> withdrawFromAccountRequest, ServerWebExchange exchange) {
        return withdrawFromAccountRequest
                .map(serviceFacade.getWithdrawMapper()::toDomain)
                .flatMap(withdraw -> serviceFacade.getTransactionService().withdraw(id, withdraw))
                .map(serviceFacade.getTransactionMapper()::toModel)
                .map(transaction -> ResponseEntity.status(HttpStatus.OK).body(transaction));
    }

    @Override
    public Mono<ResponseEntity<Flux<Transaction>>> getAccountTransactions(String id, ServerWebExchange exchange) {
        Flux<Transaction> creditsFlux = serviceFacade.getTransactionService().getTransactions(id)
                .map(serviceFacade.getTransactionMapper()::toModel);

        return Mono.just(ResponseEntity.ok(creditsFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAccountsByCustomerId(String customerId, ServerWebExchange exchange) {
        Flux<Account> creditsFlux = serviceFacade.getAccountService().findByCustomerId(customerId)
                .map(serviceFacade.getAccountMapper()::toModel);

        return Mono.just(ResponseEntity.ok(creditsFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<AverageDailyBalanceSummary>>> getAverageDailyBalance(String customerId, ServerWebExchange exchange) {
        Flux<AverageDailyBalanceSummary> creditsFlux = serviceFacade.getTransactionService().generateDailyBalanceSummary(customerId)
                .map(serviceFacade.getDailyBalanceSummaryMapper()::toModel);

        return Mono.just(ResponseEntity.ok(creditsFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
