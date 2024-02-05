package com.app.domain.clients_management.model;

import java.util.Comparator;


public interface ClientComparator {
    Comparator<Client> byPriority = Comparator
            .comparingInt((Client client) -> {
                var priority = client.priority;
                return switch (priority) {
                    case NORMAL -> 2;
                    case VIP -> 1;
                    case INSTANT -> 0;
                };
            })
            .thenComparing(client -> client.timestamp);
}
