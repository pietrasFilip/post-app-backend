package com.app.application.service;

import com.app.application.dto.client.CreateClientDto;
import com.app.application.dto.client.GetClientDto;
import com.app.application.service.post.PostServiceImpl;
import com.app.application.service.post.provider.ClientsProvider;
import com.app.application.validator.client.CreateClientDtoValidator;
import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.infrastructure.persistence.entity.ClientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.app.domain.clients_management.model.priority.Priority.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowQueueTest {
    @Mock
    ClientsProvider clientsProvider;
    @Mock
    CreateClientDtoValidator createClientDtoValidator;
    @Mock
    ClientRepositoryDb clientRepositoryDb;
    @Mock
    ApplicationEventPublisher applicationEventPublisher;
    @InjectMocks
    PostServiceImpl postService;

    @Test
    @DisplayName("When queue is empty")
    void test1() {
        assertThat(postService.showQueue())
                .isInstanceOf(List.class)
                .isEqualTo(List.of());
    }

    @Test
    @DisplayName("When contains NORMAL client")
    void test2() {
        var createClientDto = new CreateClientDto("AA", NORMAL, "");
        var expected = addClientToQueue(createClientDto);

        assertThat(postService.showQueue())
                .isInstanceOf(List.class)
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When contains VIP client")
    void test3() {
        var createClientDto = new CreateClientDto("AA", VIP, "8888");
        var expected = addClientToQueue(createClientDto);

        assertThat(postService.showQueue())
                .isInstanceOf(List.class)
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When contains INSTANT client")
    void test4() {
        var createClientDto = new CreateClientDto("AA", INSTANT, "0000");
        var expected = addClientToQueue(createClientDto);

        assertThat(postService.showQueue())
                .isInstanceOf(List.class)
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When contains INSTANT, VIP and NORMAL client")
    void test5() {
        var createClientDtoInstant = new CreateClientDto("AA", INSTANT, "0000");
        var createClientDtoNormal = new CreateClientDto("BB", NORMAL, "");
        var createClientDtoVip = new CreateClientDto("CC", VIP, "8888");

        var addedInstant = addClientToQueue(createClientDtoInstant);
        var addedNormal = addClientToQueue(createClientDtoNormal);
        var addedVip = addClientToQueue(createClientDtoVip);

        assertThat(postService.showQueue())
                .isInstanceOf(List.class)
                .isEqualTo(List.of(addedInstant, addedVip, addedNormal));
    }

    private GetClientDto addClientToQueue(CreateClientDto createClientDto) {
        when(createClientDtoValidator.validate(createClientDto))
                .thenReturn(createClientDto);

        var timestampNormal = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        when(clientRepositoryDb.save(createClientDto.toClientEntityWithTimestamp(timestampNormal)))
                .thenReturn(ClientEntity
                        .builder()
                        .id(1L)
                        .username(createClientDto.username())
                        .priority(createClientDto.priority())
                        .timestamp(timestampNormal)
                        .build());

        return postService.addToQueue(createClientDto);
    }
}
