package com.app.domain.clients_management.policy.factory.validator;

import com.app.infrastructure.persistence.entity.ClientEntity;

import java.util.List;

public interface ClientDataValidator extends DataValidator<List<ClientEntity>> {
    ClientEntity validateSingleClient(ClientEntity client);
}
