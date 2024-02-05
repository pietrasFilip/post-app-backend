package com.app.domain.clients_management.model.repository.db;

import com.app.domain.clients_management.model.priority.Priority;
import com.app.infrastructure.persistence.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryDb {
    List<ClientEntity> findAll();
    ClientEntity save(ClientEntity client);
    void delete(ClientEntity client);
    Optional<ClientEntity> findById(Long id);
    Optional<ClientEntity> findByUsername(String username);
    Optional<ClientEntity> findByPriority(Priority priority);
}
