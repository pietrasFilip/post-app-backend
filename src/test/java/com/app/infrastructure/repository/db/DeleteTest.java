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
class DeleteTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientRepositoryDb clientRepositoryDb;

    @Test
    @DisplayName("When client is deleted from db")
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

        clientRepositoryDb.delete(client2);

        assertThat(clientRepositoryDb.findAll())
                .isEqualTo(List.of(client1));
    }
}
