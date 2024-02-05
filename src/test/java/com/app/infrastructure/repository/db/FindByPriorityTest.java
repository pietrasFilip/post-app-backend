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

import java.util.Optional;

import static com.app.domain.clients_management.model.priority.Priority.NORMAL;
import static com.app.domain.clients_management.model.priority.Priority.VIP;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ClientRepositoryConfiguration.class)
class FindByPriorityTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientRepositoryDb clientRepositoryDb;

    @Test
    @DisplayName("When client with given priority is found")
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

        assertThat(clientRepositoryDb.findByPriority(VIP))
                .isEqualTo(Optional.of(client2));
    }

    @Test
    @DisplayName("When client with given priority is not found")
    void test2() {
        assertThat(clientRepositoryDb.findByPriority(VIP))
                .isNotPresent();
    }
}
