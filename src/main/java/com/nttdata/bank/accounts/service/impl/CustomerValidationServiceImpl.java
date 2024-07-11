package com.nttdata.bank.accounts.service.impl;

import com.nttdata.bank.accounts.service.CustomerValidationService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerValidationServiceImpl implements CustomerValidationService {

    private final WebClient.Builder webClientBuilder;

    public CustomerValidationServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Boolean> validateCustomerExists(String customerId) {
        return webClientBuilder.build()
                .get()
                .uri("/customers/" + customerId)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return Mono.just(true);
                    } else {
                        return Mono.error(new RuntimeException("Customer not found"));
                    }
                });
    }
}
