package com.app.infrastructure.repository.db;

import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.infrastructure.persistence.entity.ClientEntity;
import com.app.infrastructure.repository.db.config.ClientRepositoryConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.app.domain.clients_management.model.priority.Priority.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ClientRepositoryConfiguration.class)
class SaveTest {

    @Autowired
    private ClientRepositoryDb clientRepositoryDb;

    @Test
    @DisplayName("When client is saved to db")
    void test1() {
        var client = ClientEntity
                .builder()
                .username("V")
                .priority(NORMAL)
                .timestamp(123L)
                .build();

        var savedClient = clientRepositoryDb.save(client);
        assertThat(clientRepositoryDb.findAll())
                .contains(savedClient);
    }

}
