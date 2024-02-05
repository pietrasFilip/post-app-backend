package com.app.domain.clients_management.model;

import com.app.application.dto.client.GetClientDto;
import com.app.domain.clients_management.model.priority.Priority;
import com.app.infrastructure.persistence.entity.ClientEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class Client implements Comparable<Client> {
    final Long id;
    final String username;
    final Priority priority;
    final Long timestamp;
    boolean isHandling;

    public ClientEntity toEntity() {
        return ClientEntity
                .builder()
                .id(id)
                .username(username)
                .priority(priority)
                .timestamp(timestamp)
                .build();
    }

    public GetClientDto toGetClientDto() {
        return new GetClientDto(id, username, priority);
    }

    public boolean hasPriority(Priority priority) {
        return this.priority == priority;
    }

    public boolean isCurrentlyHandling() {
        return this.isHandling;
    }

    public void changeState(boolean state) {
        this.isHandling = state;
    }

    public boolean hasUsername(String username) {
        return this.username.equals(username);
    }

    public boolean hasId(Long id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", priority=" + priority +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int compareTo(Client o) {
        return this.priority.compareTo(o.priority);
    }
}
