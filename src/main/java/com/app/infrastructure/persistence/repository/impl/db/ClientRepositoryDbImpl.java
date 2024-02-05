package com.app.infrastructure.persistence.repository.impl.db;

import com.app.domain.clients_management.model.priority.Priority;
import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.infrastructure.persistence.entity.ClientEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.ClientEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryDbImpl implements ClientRepositoryDb {
    private final ClientEntityDao clientEntityDao;

    @Override
    public List<ClientEntity> findAll() {
        return clientEntityDao.findAll();
    }

    @Override
    public ClientEntity save(ClientEntity client) {
        return clientEntityDao.save(client);
    }

    @Override
    public void delete(ClientEntity client) {
        clientEntityDao.delete(client);
    }

    @Override
    public Optional<ClientEntity> findById(Long id) {
        return clientEntityDao.findById(id);
    }

    @Override
    public Optional<ClientEntity> findByUsername(String username) {
        return clientEntityDao.findByUsername(username);
    }

    @Override
    public Optional<ClientEntity> findByPriority(Priority priority) {
        return clientEntityDao.findByPriority(priority);
    }
}
