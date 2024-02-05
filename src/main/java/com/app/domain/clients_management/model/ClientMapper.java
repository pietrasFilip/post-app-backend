package com.app.domain.clients_management.model;

import com.app.domain.clients_management.model.priority.Priority;

import java.util.function.Function;

public interface ClientMapper {
    Function<Client, Priority> toPriority = client -> client.priority;
}
