package com.nttdata.bank.accounts.mapper;

import com.nttdata.bank.accounts.domain.ValidateRequest;
import com.nttdata.bank.accounts.model.ValidationType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ValidateTypeMapper implements EntityMapper<ValidationType, ValidateRequest>{
    @Override
    public ValidateRequest toDomain(ValidationType model) {
        ValidateRequest request = new ValidateRequest();
        BeanUtils.copyProperties(model, request);
        return request;
    }

    @Override
    public ValidationType toModel(ValidateRequest domain) {
        ValidationType model = new ValidationType();
        BeanUtils.copyProperties(domain, model);
        return model;
    }
}