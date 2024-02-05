package com.app.infrastructure.persistence.entity;

import com.app.domain.clients_management.model.Client;
import com.app.domain.clients_management.model.priority.Priority;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private Priority priority;
    private Long timestamp;

    public Client toDomain() {
        return Client
                .builder()
                .id(id)
                .username(username)
                .priority(priority)
                .timestamp(timestamp)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
