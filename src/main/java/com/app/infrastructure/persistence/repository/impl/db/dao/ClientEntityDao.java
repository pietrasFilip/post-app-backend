package com.app.infrastructure.persistence.repository.impl.db.dao;

import com.app.domain.clients_management.model.priority.Priority;
import com.app.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientEntityDao extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByUsername(String username);
    Optional<ClientEntity> findByPriority(Priority priority);
}
