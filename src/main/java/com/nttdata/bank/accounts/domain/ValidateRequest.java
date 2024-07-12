package com.nttdata.bank.accounts.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequest {
    private String type;
    private Double balance;
}