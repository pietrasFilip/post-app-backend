package com.app.domain.clients_management.policy.factory.converter;

import com.app.domain.clients_management.model.Client;
import com.app.infrastructure.persistence.entity.ClientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.app.domain.clients_management.model.priority.Priority.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

class ConvertTest {

    @Test
    @DisplayName("When converting from ClientEntity to Client")
    void test1() {
        var converter = new ToClientConverterImpl();
        var entity = ClientEntity
                .builder()
                .id(1L)
                .username("A")
                .priority(NORMAL)
                .timestamp(123L)
                .build();

        var expected = Client
                .builder()
                .id(1L)
                .username("A")
                .priority(NORMAL)
                .timestamp(123L)
                .build();

        assertThat(converter.convert(List.of(entity)))
                .isInstanceOf(List.class)
                .isEqualTo(List.of(expected));
    }
}
