package com.app.domain.clients_management.policy.factory.config;

import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.domain.clients_management.policy.factory.factory.FromDbToClientWithValidator;
import com.app.domain.clients_management.policy.factory.loader.client.db.ClientDataDbLoader;
import com.app.domain.clients_management.policy.factory.loader.client.db.ClientDataDbLoaderImpl;
import com.app.domain.clients_management.policy.factory.processor.ClientDataProcessor;
import com.app.domain.clients_management.policy.factory.processor.impl.ClientDataProcessorDbImpl;
import com.app.domain.clients_management.policy.factory.validator.ClientDataValidator;
import com.app.domain.clients_management.policy.factory.validator.impl.ClientDataValidatorImpl;
import com.app.infrastructure.persistence.repository.impl.db.ClientRepositoryDbImpl;
import com.app.infrastructure.persistence.repository.impl.db.dao.ClientEntityDao;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FactoryConfig {
    @Bean
    public ClientRepositoryDb clientRepositoryDb(ClientEntityDao clientEntityDao) {
        return new ClientRepositoryDbImpl(clientEntityDao);
    }

    @Bean
    public ClientDataDbLoader clientDataDbLoader(ClientRepositoryDb clientRepositoryDb) {
        return new ClientDataDbLoaderImpl(clientRepositoryDb);
    }

    @Bean
    public ClientDataValidator clientDataValidator() {
        return new ClientDataValidatorImpl("[A-Z ]+");
    }

    @Bean
    public FromDbToClientWithValidator fromDbToClientWithValidator(ClientRepositoryDb clientRepositoryDb,
                                                                   ClientDataValidator clientDataValidator) {
        return new FromDbToClientWithValidator(clientRepositoryDb, clientDataValidator);
    }

    @Bean
    public ClientDataProcessor clientDataProcessorDb(ClientRepositoryDb clientRepositoryDb) {
        return new ClientDataProcessorDbImpl(fromDbToClientWithValidator(clientRepositoryDb, clientDataValidator()));
    }
}
