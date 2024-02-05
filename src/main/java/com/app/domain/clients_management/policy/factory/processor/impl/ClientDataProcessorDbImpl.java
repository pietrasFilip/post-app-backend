package com.app.domain.clients_management.policy.factory.processor.impl;

import com.app.domain.clients_management.model.Client;
import com.app.domain.clients_management.policy.factory.converter.Converter;
import com.app.domain.clients_management.policy.factory.factory.FromDbToClientWithValidator;
import com.app.domain.clients_management.policy.factory.loader.DataLoader;
import com.app.domain.clients_management.policy.factory.processor.ClientDataProcessor;
import com.app.domain.clients_management.policy.factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.ClientEntity;

import java.util.List;

public class ClientDataProcessorDbImpl implements ClientDataProcessor {
    private final DataLoader<List<ClientEntity>> dataLoader;
    private final DataValidator<List<ClientEntity>> dataValidator;
    private final Converter<List<ClientEntity>, List<Client>> converter;

    public ClientDataProcessorDbImpl(FromDbToClientWithValidator dataFactory) {
        this.dataLoader = dataFactory.createLoader();
        this.dataValidator = dataFactory.createValidator();
        this.converter = dataFactory.createConverter();
    }

    @Override
    public List<Client> process() {
        var loadedData = dataLoader.load();
        var validatedData = dataValidator.validate(loadedData);
        return converter.convert(validatedData);
    }
}
