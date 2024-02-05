package com.app.domain.clients_management.policy.factory.converter;

import com.app.domain.clients_management.model.Client;
import com.app.infrastructure.persistence.entity.ClientEntity;

import java.util.List;

public class ToClientConverterImpl implements ToClientConverter{
    @Override
    public List<Client> convert(List<ClientEntity> data) {
        return data
                .stream()
                .map(ClientEntity::toDomain)
                .toList();
    }
}
