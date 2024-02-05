package com.app.application.service.post;

import com.app.application.dto.client.CreateClientDto;
import com.app.application.dto.client.GetClientDto;
import com.app.application.service.post.queue_monitor.QueueMonitorStatistics;
import com.app.domain.clients_management.model.Client;

import java.util.List;


public interface PostService {
    GetClientDto addToQueue(CreateClientDto clientDto);
    void deleteClient(Client client);
    List<GetClientDto> showQueue();
    QueueMonitorStatistics monitorQueueByUsername(String username);
    QueueMonitorStatistics monitorQueueById(String id);
    void checkForClients();
    Client findFirstNonInstantClient();
}
