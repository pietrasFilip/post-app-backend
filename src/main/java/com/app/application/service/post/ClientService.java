package com.app.application.service.post;

import com.app.domain.clients_management.model.Client;

import java.util.concurrent.CompletableFuture;

public interface ClientService {
    CompletableFuture<Void> handleClient(Client client);
}
