package com.app.domain.clients_management.policy.factory.converter;

public interface Converter <T, U> {
    U convert(T data);
}
