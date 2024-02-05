package com.app.domain.clients_management.policy.factory.loader.client.db;

import com.app.domain.clients_management.policy.factory.loader.DataLoader;
import com.app.infrastructure.persistence.entity.ClientEntity;

import java.util.List;

public interface ClientDataDbLoader extends DataLoader<List<ClientEntity>> {
}
