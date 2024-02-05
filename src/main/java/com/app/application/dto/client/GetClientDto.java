package com.app.application.dto.client;

import com.app.domain.clients_management.model.priority.Priority;

public record GetClientDto(Long id, String username, Priority priority){
}
