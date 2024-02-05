package com.app.domain.clients_management.policy.factory.loader;

import com.app.domain.clients_management.policy.factory.config.FactoryConfig;
import com.app.domain.clients_management.policy.factory.loader.client.db.ClientDataDbLoader;
import com.app.infrastructure.persistence.entity.ClientEntity;
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
@Import(FactoryConfig.class)
class LoadTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientDataDbLoader clientDataDbLoader;

    @Test
    @DisplayName("When there are ClientEntity to load from db")
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

        assertThat(clientDataDbLoader.load())
                .isEqualTo(List.of(client1, client2));
    }
}
