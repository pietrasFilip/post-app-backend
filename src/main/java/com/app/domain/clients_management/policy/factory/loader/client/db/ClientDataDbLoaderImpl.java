package com.app.domain.clients_management.policy.factory.loader.client.db;

import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.infrastructure.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ClientDataDbLoaderImpl implements ClientDataDbLoader {
    private final ClientRepositoryDb clientRepository;
    @Override
    public List<ClientEntity> load() {
        return clientRepository.findAll();
    }
}
