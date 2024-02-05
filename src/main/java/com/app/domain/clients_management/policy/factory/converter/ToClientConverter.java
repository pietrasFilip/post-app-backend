package com.app.domain.clients_management.policy.factory.converter;

import com.app.domain.clients_management.model.Client;
import com.app.infrastructure.persistence.entity.ClientEntity;

import java.util.List;

public interface ToClientConverter extends Converter<List<ClientEntity>, List<Client>> {
}
