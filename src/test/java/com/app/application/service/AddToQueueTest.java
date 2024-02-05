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
import java.util.Optional;

import static com.app.domain.clients_management.model.priority.Priority.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddToQueueTest {
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
    @DisplayName("When priority is VIP and password is correct")
    void test1() {
        var expectedClient = new GetClientDto(1L, "AA", VIP);
        var createClientDto = new CreateClientDto("AA", VIP, "8888");

        when(createClientDtoValidator.validate(createClientDto))
                .thenReturn(createClientDto);

        when(clientRepositoryDb.findByUsername("AA"))
                .thenReturn(Optional.empty());

        var timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        when(clientRepositoryDb.save(createClientDto.toClientEntityWithTimestamp(timestamp)))
                .thenReturn(ClientEntity
                        .builder()
                        .id(1L)
                        .username("AA")
                        .priority(VIP)
                        .timestamp(timestamp)
                        .build());

        assertThat(postService.addToQueue(createClientDto))
                .isInstanceOf(GetClientDto.class)
                .isEqualTo(expectedClient);
    }

    @Test
    @DisplayName("When priority is VIP and password is incorrect")
    void test2() {
        var createClientDto = new CreateClientDto("AA", VIP, "1234");

        when(createClientDtoValidator.validate(createClientDto))
                .thenThrow(new IllegalAccessError("Wrong input format!"));

        assertThatThrownBy(() -> postService.addToQueue(createClientDto))
                .isInstanceOf(IllegalAccessError.class)
                .hasMessage("Wrong input format!");
    }

    @Test
    @DisplayName("When priority is VIP and password is empty")
    void test3() {
        var createClientDto = new CreateClientDto("AA", VIP, "");

        when(createClientDtoValidator.validate(createClientDto))
                .thenThrow(new IllegalAccessError("Password is null or empty"));

        assertThatThrownBy(() -> postService.addToQueue(createClientDto))
                .isInstanceOf(IllegalAccessError.class)
                .hasMessage("Password is null or empty");
    }

    @Test
    @DisplayName("When priority is INSTANT and password is correct")
    void test4() {
        var expectedClient = new GetClientDto(1L, "AA", INSTANT);
        var createClientDto = new CreateClientDto("AA", INSTANT, "0000");

        when(createClientDtoValidator.validate(createClientDto))
                .thenReturn(createClientDto);

        when(clientRepositoryDb.findByUsername("AA"))
                .thenReturn(Optional.empty());

        var timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        when(clientRepositoryDb.save(createClientDto.toClientEntityWithTimestamp(timestamp)))
                .thenReturn(ClientEntity
                        .builder()
                        .id(1L)
                        .username("AA")
                        .priority(INSTANT)
                        .timestamp(timestamp)
                        .build());

        assertThat(postService.addToQueue(createClientDto))
                .isInstanceOf(GetClientDto.class)
                .isEqualTo(expectedClient);
    }

    @Test
    @DisplayName("When priority is INSTANT and password is incorrect")
    void test5() {
        var createClientDto = new CreateClientDto("AA", INSTANT, "1234");

        when(createClientDtoValidator.validate(createClientDto))
                .thenThrow(new IllegalAccessError("Wrong input format!"));

        assertThatThrownBy(() -> postService.addToQueue(createClientDto))
                .isInstanceOf(IllegalAccessError.class)
                .hasMessage("Wrong input format!");
    }

    @Test
    @DisplayName("When priority is INSTANT and password is empty")
    void test6() {
        var createClientDto = new CreateClientDto("AA", INSTANT, "");

        when(createClientDtoValidator.validate(createClientDto))
                .thenThrow(new IllegalAccessError("Password is null or empty"));

        assertThatThrownBy(() -> postService.addToQueue(createClientDto))
                .isInstanceOf(IllegalAccessError.class)
                .hasMessage("Password is null or empty");
    }

    @Test
    @DisplayName("When priority is NORMAL and password is correct")
    void test7() {
        var expectedClient = new GetClientDto(1L, "AA", NORMAL);
        var createClientDto = new CreateClientDto("AA", NORMAL, "");

        when(createClientDtoValidator.validate(createClientDto))
                .thenReturn(createClientDto);

        when(clientRepositoryDb.findByUsername("AA"))
                .thenReturn(Optional.empty());

        var timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        when(clientRepositoryDb.save(createClientDto.toClientEntityWithTimestamp(timestamp)))
                .thenReturn(ClientEntity
                        .builder()
                        .id(1L)
                        .username("AA")
                        .priority(NORMAL)
                        .timestamp(timestamp)
                        .build());

        assertThat(postService.addToQueue(createClientDto))
                .isInstanceOf(GetClientDto.class)
                .isEqualTo(expectedClient);
    }

    @Test
    @DisplayName("When priority is NORMAL and password is incorrect")
    void test8() {
        var createClientDto = new CreateClientDto("AA", NORMAL, "1234");

        when(createClientDtoValidator.validate(createClientDto))
                .thenThrow(new IllegalAccessError("No password required!"));

        assertThatThrownBy(() -> postService.addToQueue(createClientDto))
                .isInstanceOf(IllegalAccessError.class)
                .hasMessage("No password required!");
    }
}
