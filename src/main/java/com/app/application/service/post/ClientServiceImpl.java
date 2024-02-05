package com.app.application.service.post;


import com.app.domain.clients_management.model.Client;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.app.domain.clients_management.model.priority.Priority.INSTANT;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final Long normalVipClientHandleTime;
    private final Long instantClientHandleTime;
    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public CompletableFuture<Void> handleClient(Client client) {
        var priority = client.toGetClientDto().priority();
        if (priority != INSTANT) {
            logger.info("HANDLING {}", client.toGetClientDto());
            simulateProcessing(normalVipClientHandleTime);
        }
        if (priority == INSTANT) {
            logger.info("HANDLING {}", client.toGetClientDto());
            simulateProcessing(instantClientHandleTime);
        }

        return CompletableFuture.completedFuture(null);
    }

    private void simulateProcessing(Long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
