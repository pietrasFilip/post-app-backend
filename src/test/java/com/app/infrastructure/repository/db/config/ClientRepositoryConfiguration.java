package com.app.infrastructure.repository.db.config;

import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.infrastructure.persistence.repository.impl.db.ClientRepositoryDbImpl;
import com.app.infrastructure.persistence.repository.impl.db.dao.ClientEntityDao;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ClientRepositoryConfiguration {
    @Bean
    public ClientRepositoryDb clientRepositoryDb(ClientEntityDao clientEntityDao) {
        return new ClientRepositoryDbImpl(clientEntityDao);
    }
}
