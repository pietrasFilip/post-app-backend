package com.app.infrastructure.persistence.repository.provider.impl;

import com.app.application.service.post.provider.ClientsProvider;
import com.app.domain.clients_management.model.Client;
import com.app.domain.clients_management.policy.factory.processor.ClientDataProcessor;
import com.app.domain.clients_management.policy.factory.processor.type.ProcessorType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientsProviderImpl implements ClientsProvider {

    @Value("${processor.type}")
    private String processorType;
    private final ClientDataProcessor clientDataDbProcessor;

    @Override
    public List<Client> provide() {
        return switch (ProcessorType.valueOf(processorType)) {
            case FROM_DB_TO_CLIENT_WITH_VALIDATOR -> clientDataDbProcessor.process();
        };
    }
}
