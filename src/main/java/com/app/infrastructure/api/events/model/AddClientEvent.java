package com.app.infrastructure.api.events.model;

import com.app.domain.clients_management.model.Client;
import com.app.domain.clients_management.model.priority.Priority;

public record AddClientEvent(Client client, Priority priority) { }
