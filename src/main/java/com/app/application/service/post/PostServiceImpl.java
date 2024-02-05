package com.app.application.service.post;

import com.app.application.dto.client.CreateClientDto;
import com.app.application.dto.client.GetClientDto;
import com.app.application.service.post.provider.ClientsProvider;
import com.app.application.service.post.queue_monitor.QueueMonitorStatistics;
import com.app.application.validator.client.CreateClientDtoValidator;
import com.app.domain.clients_management.model.Client;
import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.infrastructure.api.events.model.AddClientEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.PriorityQueue;

import static com.app.domain.clients_management.model.ClientComparator.byPriority;
import static com.app.domain.clients_management.model.ClientMapper.toPriority;
import static com.app.domain.clients_management.model.priority.Priority.INSTANT;

public class PostServiceImpl implements PostService {
    private final PriorityQueue<Client> queueMap;
    private final CreateClientDtoValidator createClientDtoValidator;
    private final ClientRepositoryDb clientRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final Logger logger = LogManager.getRootLogger();

    public PostServiceImpl(ClientsProvider clientsProvider, CreateClientDtoValidator createClientDtoValidator,
                           ClientRepositoryDb clientRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.queueMap = init(clientsProvider);
        this.createClientDtoValidator = createClientDtoValidator;
        this.clientRepository = clientRepository;
    }

    /**
     * Inits queue with clients
     *
     * @param clientsProvider - provides clients from db
     * @return - PriorityQueue of clients
     */
    private PriorityQueue<Client> init(ClientsProvider clientsProvider) {
        var queue = new PriorityQueue<>(byPriority);
        queue.addAll(clientsProvider.provide());

        return queue;
    }

    /**
     * Checks if there are clients in queue. If yes, sends as many events as clients in queue.
     */
    @Override
    public void checkForClients() {
        toSortedClientList()
                .forEach(client -> {
                    var toAdd = new AddClientEvent(client, client.toGetClientDto().priority());
                    applicationEventPublisher.publishEvent(toAdd);
                });
    }

    /**
     * Finds first non-instant client
     * @return - first in queue non-instant client
     */
    @Override
    public Client findFirstNonInstantClient() {
        return queueMap
                .stream()
                .toList()
                .stream()
                .filter(client -> !client.hasPriority(INSTANT))
                .min(byPriority)
                .orElseThrow();
    }

    /**
     * Adds client to queue
     *
     * @param clientDto - required client data from form
     * @return - added client
     */
    @Override
    public GetClientDto addToQueue(CreateClientDto clientDto) {
        var validatedClient = createClientDtoValidator.validate(clientDto);

        if (clientRepository.findByUsername(clientDto.username()).isPresent()) {
            logger.error("User with {} username already exists!", clientDto.username());
            throw new IllegalStateException("User with this username already exists!");
        }

        if (clientDto.priority() == INSTANT && clientRepository.findByPriority(INSTANT).isPresent()) {
            logger.error("There can be only one INSTANT Client at the time");
            throw new IllegalStateException("Instant client already exists!");
        }

        var timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        var savedClient = clientRepository
                .save(validatedClient.toClientEntityWithTimestamp(timestamp));

        var clientToQueue = savedClient.toDomain();
        clientToQueue.changeState(false);
        queueMap.add(clientToQueue);

        applicationEventPublisher.publishEvent(new AddClientEvent(clientToQueue, validatedClient.priority()));

        return new GetClientDto(savedClient.getId(), savedClient.getUsername(), savedClient.getPriority());
    }

    /**
     * Deletes client when one has been handled
     *
     * @param client - client to delete
     */
    @Override
    public void deleteClient(Client client) {
        // uzywam remove zamiast poll, poniewaz moze byc tak, ze w trakcie obslugi NORMAL pojawia sie VIP i kolejka ma
        // VIP na pierwszym miejscu.
        var isDeleted = queueMap.remove(client);
        logger.info("DELETED {}", client.toGetClientDto());

        if (isDeleted) {
            clientRepository.delete(client.toEntity());
            logger.info("HANDLED {}", client.toGetClientDto());
        }
    }

    /**
     * Shows current queue
     *
     * @return - current queue
     */
    @Override
    public List<GetClientDto> showQueue() {
        return toSortedClientList().stream().map(Client::toGetClientDto).toList();
    }

    private List<Client> toSortedClientList() {
        return queueMap.stream().toList().stream().sorted(byPriority).toList();
    }

    /**
     * Informs about estimated time and amount of clients ahead of client with given username
     *
     * @param username - client username
     * @return - estimated time and amount of clients ahead of client
     */
    @Override
    public QueueMonitorStatistics monitorQueueByUsername(String username) {
        var selectedClient = queueMap
                .stream()
                .filter(client -> client.hasUsername(username))
                .findFirst();

        if (selectedClient.isEmpty()) {
            throw new IllegalStateException("No client with this username found");
        }

        var selectedClientToDomain = selectedClient.get();
        return getStatistic(selectedClientToDomain);
    }

    /**
     * Informs about estimated time and amount of clients ahead of client with given id
     *
     * @param id - client id
     * @return - estimated time and amount of clients ahead of client
     */
    @Override
    public QueueMonitorStatistics monitorQueueById(String id) {
        var selectedClient = queueMap
                .stream()
                .filter(client -> client.hasId(Long.valueOf(id)))
                .findFirst();

        if (selectedClient.isEmpty()) {
            throw new IllegalStateException("No client with this id found");
        }

        var selectedClientToDomain = selectedClient.get();
        return getStatistic(selectedClientToDomain);
    }

    private QueueMonitorStatistics getStatistic(Client selectedClient) {
        var clients = queueMap.stream().sorted(byPriority).toList();

        var clientsCount = countClients(clients, selectedClient);
        var timeCount = countTime(clients, selectedClient);

        return new QueueMonitorStatistics(clientsCount, timeCount);
    }

    private int countClients(List<Client> clients, Client selectedClient) {
        var clientIdx = clients.indexOf(selectedClient);

        if (selectedClient.isCurrentlyHandling() || selectedClient.hasPriority(INSTANT)) {
            return 0;
        }

        if (!selectedClient.isCurrentlyHandling() && clientIdx == 0) {
            return 1;
        }

        return clients.subList(0, clientIdx).size();
    }

    private long countTime(List<Client> clients, Client selectedClient) {
        var clientIdx = clients.indexOf(selectedClient);

        if (selectedClient.isCurrentlyHandling() || selectedClient.hasPriority(INSTANT)) {
            return 0;
        }

        if (!selectedClient.isCurrentlyHandling() && clientIdx == 0) {
            return 20;
        }

        return sumTime(clients, clientIdx);
    }

    private long sumTime(List<Client> clients, int clientIdx) {
        return clients.subList(0, clientIdx)
                .stream()
                .map(toPriority)
                .mapToLong(priority -> priority == INSTANT ? 60 : 20)
                .sum();
    }
}
