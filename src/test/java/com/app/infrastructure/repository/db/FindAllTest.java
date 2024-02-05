package com.app.infrastructure.repository.db;

import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.infrastructure.persistence.entity.ClientEntity;
import com.app.infrastructure.repository.db.config.ClientRepositoryConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.app.domain.clients_management.model.priority.Priority.NORMAL;
import static com.app.domain.clients_management.model.priority.Priority.VIP;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ClientRepositoryConfiguration.class)
class FindAllTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientRepositoryDb clientRepositoryDb;

    @Test
    @DisplayName("When there are clients in database")
    void test1() {
        var client1 = ClientEntity
                .builder()
                .username("A")
                .priority(NORMAL)
                .timestamp(123L)
                .build();

        var client2 = ClientEntity
                .builder()
                .username("B")
                .priority(VIP)
                .timestamp(124L)
                .build();

        testEntityManager.persist(client1);
        testEntityManager.persist(client2);
        testEntityManager.flush();

        var foundClients = clientRepositoryDb.findAll();
        var expectedClients = List.of(client1, client2);

        assertThat(foundClients)
                .isInstanceOf(List.class)
                .isEqualTo(expectedClients);
    }

    @Test
    @DisplayName("When there are no clients in database")
    void test2() {
        var foundClients = clientRepositoryDb.findAll();
        var expectedClients = List.of();

        assertThat(foundClients)
                .isInstanceOf(List.class)
                .isEqualTo(expectedClients);
    }
}
