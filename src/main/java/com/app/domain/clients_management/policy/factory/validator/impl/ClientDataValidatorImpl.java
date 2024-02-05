package com.app.domain.clients_management.policy.factory.validator.impl;

import com.app.domain.clients_management.policy.factory.validator.ClientDataValidator;
import com.app.infrastructure.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.domain.clients_management.policy.factory.validator.DataValidator.validateMatchesRegex;
import static com.app.domain.clients_management.policy.factory.validator.DataValidator.validateNull;

@RequiredArgsConstructor
public class ClientDataValidatorImpl implements ClientDataValidator {
    private final String usernameRegex;

    @Override
    public List<ClientEntity> validate(List<ClientEntity> clientEntities) {
        return clientEntities
                .stream()
                .map(this::validateSingleClient)
                .toList();
    }

    @Override
    public ClientEntity validateSingleClient(ClientEntity client) {
        return ClientEntity
                .builder()
                .id(validateNull(client.getId()))
                .username(validateMatchesRegex(client.getUsername(), usernameRegex))
                .priority(client.getPriority())
                .timestamp(client.getTimestamp())
                .build();
    }
}
