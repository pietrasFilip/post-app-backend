package com.app.application.service.post.provider;

import com.app.domain.clients_management.model.Client;

import java.util.List;

public interface ClientsProvider {
    List<Client> provide();
}
