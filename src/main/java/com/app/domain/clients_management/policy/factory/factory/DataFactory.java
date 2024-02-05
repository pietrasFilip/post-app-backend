package com.app.domain.clients_management.policy.factory.factory;

import com.app.domain.clients_management.policy.factory.converter.Converter;
import com.app.domain.clients_management.policy.factory.loader.DataLoader;
import com.app.domain.clients_management.policy.factory.validator.DataValidator;

public interface DataFactory <T, U> {
    DataLoader<T> createLoader();
    DataValidator<T> createValidator();
    Converter<T, U> createConverter();
}
