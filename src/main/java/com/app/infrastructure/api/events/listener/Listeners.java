package com.app.infrastructure.api.events.listener;

import com.app.application.service.post.ClientService;
import com.app.application.service.post.PostService;
import com.app.domain.clients_management.model.Client;
import com.app.infrastructure.api.events.model.AddClientEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.app.domain.clients_management.model.priority.Priority.INSTANT;

@Component
@RequiredArgsConstructor
public class Listeners {
    private final ClientService clientService;
    private final PostService postService;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Client currentNormalVipClient;

    @EventListener
    public void handleNormalVipClientEvent(AddClientEvent addClientEvent) {
        var clientPriority = addClientEvent.priority();

        if (clientPriority != INSTANT) {
            executorService.submit(() -> {
                currentNormalVipClient = postService.findFirstNonInstantClient();
                currentNormalVipClient.changeState(true);
                clientService
                        .handleClient(currentNormalVipClient)
                        .thenRun(() -> postService.deleteClient(currentNormalVipClient));
            });
        }

        if (clientPriority == INSTANT) {
            executorService.shutdownNow();
            this.executorService = Executors.newSingleThreadExecutor();

            executorService.submit(() -> {
                currentNormalVipClient.changeState(false);
                var client = addClientEvent.client();
                clientService
                        .handleClient(client)
                        .thenRun(() -> postService.deleteClient(client))
                        .thenRun(postService::checkForClients);
            });
        }
    }

    @EventListener
    public void handleAppStart(ApplicationStartedEvent applicationStartedEvent) {
        postService.checkForClients();
    }
}
