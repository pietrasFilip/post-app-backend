package com.app.application.dto.client;

import com.app.domain.clients_management.model.priority.Priority;
import com.app.infrastructure.persistence.entity.ClientEntity;

public record CreateClientDto(String username, Priority priority, String password) {
    public ClientEntity toClientEntityWithTimestamp(Long timestamp) {
        return ClientEntity
                .builder()
                .username(username)
                .priority(priority)
                .timestamp(timestamp)
                .build();
    }
}
