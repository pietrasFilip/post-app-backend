package com.app.domain.clients_management.policy.factory.factory;

import com.app.domain.clients_management.model.Client;
import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.domain.clients_management.policy.factory.converter.Converter;
import com.app.domain.clients_management.policy.factory.converter.ToClientConverterImpl;
import com.app.domain.clients_management.policy.factory.loader.DataLoader;
import com.app.domain.clients_management.policy.factory.loader.client.db.ClientDataDbLoaderImpl;
import com.app.domain.clients_management.policy.factory.validator.ClientDataValidator;
import com.app.domain.clients_management.policy.factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromDbToClientWithValidator implements DataFactory<List<ClientEntity>, List<Client>> {
    private final ClientRepositoryDb clientRepository;
    private final ClientDataValidator clientDataValidator;
    @Override
    public DataLoader<List<ClientEntity>> createLoader() {
        return new ClientDataDbLoaderImpl(clientRepository);
    }

    @Override
    public DataValidator<List<ClientEntity>> createValidator() {
        return clientDataValidator;
    }

    @Override
    public Converter<List<ClientEntity>, List<Client>> createConverter() {
        return new ToClientConverterImpl();
    }
}
