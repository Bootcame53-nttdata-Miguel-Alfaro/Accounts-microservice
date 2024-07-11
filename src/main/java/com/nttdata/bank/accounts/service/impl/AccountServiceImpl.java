package com.nttdata.bank.accounts.service.impl;

import com.nttdata.bank.accounts.domain.Account;
import com.nttdata.bank.accounts.domain.Balance;
import com.nttdata.bank.accounts.repository.AccountRepository;
import com.nttdata.bank.accounts.service.AccountService;
import com.nttdata.bank.accounts.service.CustomerValidationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerValidationService customerValidationService;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerValidationService customerValidationService) {
        this.accountRepository = accountRepository;
        this.customerValidationService = customerValidationService;
    }

    @Override
    public Mono<Account> save(Mono<Account> account) {
        return account
                .flatMap(acc -> {
                    String accountUsageType = acc.getAccountUsageType();

                    return Mono.just(accountUsageType)
                            .filter(type -> "empresarial".equals(type) || "personal".equals(type))
                            .switchIfEmpty(Mono.error(new RuntimeException("The type must be 'empresarial' or 'personal'")))
                            .then(customerValidationService.validateCustomerExists(acc.getCustomerId()))
                            .flatMap(customerExists -> {
                                if (!customerExists) {
                                    return Mono.error(new RuntimeException("Customer ID does not exist"));
                                }
                                return accountRepository.findByCustomerId(acc.getCustomerId()).collectList();
                            })
                            .flatMap(existingAccounts -> validateAccount(acc, existingAccounts))
                            .then(accountRepository.save(acc));
                });
    }

    private Mono<Void> validateAccount(Account acc, List<Account> existingAccounts) {
        String accountUsageType = acc.getAccountUsageType();
        String accountType = acc.getAccountType();

        // Validar que el accountType solo sea "ahorro", "corriente" o "plazo_fijo"
        if (!"ahorro".equals(accountType) && !"corriente".equals(accountType) && !"plazo_fijo".equals(accountType)) {
            return Mono.error(new RuntimeException("The accountType must be 'ahorro', 'corriente', or 'plazo_fijo'"));
        }

        if ("personal".equals(accountUsageType)) {
            // Contar las cuentas de tipo ahorro, corriente o plazo fijo existentes
            long count = existingAccounts.stream()
                    .filter(a -> "ahorro".equals(a.getAccountType()) ||
                            "corriente".equals(a.getAccountType()) ||
                            "plazo_fijo".equals(a.getAccountType()))
                    .count();
            if (count >= 1) {
                return Mono.error(new RuntimeException("A personal client can only have a maximum of one savings, checking, or fixed-term account"));
            }
        } else if ("empresarial".equals(accountUsageType)) {
            // Verificar si el tipo de cuenta que se está intentando crear es "ahorro" o "plazo_fijo"
            if ("ahorro".equals(accountType) || "plazo_fijo".equals(accountType)) {
                return Mono.error(new RuntimeException("A business client cannot have a savings or fixed-term account, only checking accounts."));
            }
        }
        return Mono.empty();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public Mono<Account> update(String id, Mono<Account> account) {
        return accountRepository.findById(id)
                .flatMap(a -> account)
                .doOnNext(e -> e.setId(id))
                .flatMap(accountRepository::save);
    }

    @Override
    public Mono<Void> delete(String id) {
        return accountRepository.findById(id)
                .flatMap(accountRepository::delete);
    }

    @Override
    public Mono<Balance> getBalance(String id) {
        return accountRepository.findById(id)
        .map(a -> {
           Balance b = new Balance();
           b.setCurrentBalance(a.getBalance());
           return b;
        });
    }

    @Override
    public Flux<Account> findByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
