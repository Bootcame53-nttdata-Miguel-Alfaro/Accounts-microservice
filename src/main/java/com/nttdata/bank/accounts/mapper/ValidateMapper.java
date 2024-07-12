package com.nttdata.bank.accounts.mapper;

import com.nttdata.bank.accounts.domain.ValidateResponse;
import com.nttdata.bank.accounts.model.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ValidateMapper implements EntityMapper<Validate, ValidateResponse>{
    @Override
    public ValidateResponse toDomain(Validate model) {
        ValidateResponse response = new ValidateResponse();
        BeanUtils.copyProperties(model, response);
        return response;
    }

    @Override
    public Validate toModel(ValidateResponse domain) {
        Validate model = new Validate();
        BeanUtils.copyProperties(domain, model);
        return model;
    }
}
