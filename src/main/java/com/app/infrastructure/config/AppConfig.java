package com.app.infrastructure.config;

import com.app.application.service.post.ClientService;
import com.app.application.service.post.ClientServiceImpl;
import com.app.application.service.post.PostService;
import com.app.application.service.post.PostServiceImpl;
import com.app.application.service.post.provider.ClientsProvider;
import com.app.application.validator.client.CreateClientDtoValidator;
import com.app.application.validator.client.impl.CreateClientDtoValidatorImpl;
import com.app.domain.clients_management.model.repository.db.ClientRepositoryDb;
import com.app.domain.clients_management.policy.factory.factory.FromDbToClientWithValidator;
import com.app.domain.clients_management.policy.factory.loader.client.db.ClientDataDbLoader;
import com.app.domain.clients_management.policy.factory.loader.client.db.ClientDataDbLoaderImpl;
import com.app.domain.clients_management.policy.factory.processor.ClientDataProcessor;
import com.app.domain.clients_management.policy.factory.processor.impl.ClientDataProcessorDbImpl;
import com.app.domain.clients_management.policy.factory.validator.ClientDataValidator;
import com.app.domain.clients_management.policy.factory.validator.impl.ClientDataValidatorImpl;
import com.app.infrastructure.persistence.repository.provider.impl.ClientsProviderImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.app")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class AppConfig {
    private final Environment environment;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Bean
    public CreateClientDtoValidator createClientDtoValidator() {
        return new CreateClientDtoValidatorImpl(
                environment.getRequiredProperty("validator.regex.client.username"),
                environment.getRequiredProperty("validator.password.instant"),
                environment.getRequiredProperty("validator.password.vip")
        );
    }

    // -----------------------------------------------------------------------------------
    // ABSTRACT FACTORY
    // -----------------------------------------------------------------------------------
    @Bean
    public ClientDataDbLoader clientDataDbLoader(ClientRepositoryDb clientRepository) {
        return new ClientDataDbLoaderImpl(clientRepository);
    }

    @Bean
    public ClientDataValidator clientDataValidator() {
        return new ClientDataValidatorImpl(environment
                .getRequiredProperty("validator.regex.client.username"));
    }

    @Bean
    public FromDbToClientWithValidator fromDbToClientWithValidator(ClientRepositoryDb clientRepository,
                                                                   ClientDataValidator clientDataValidator) {
        return new FromDbToClientWithValidator(clientRepository, clientDataValidator);
    }

    @Bean
    public ClientDataProcessor clientDataProcessor(ClientRepositoryDb clientRepository) {
        return new ClientDataProcessorDbImpl(fromDbToClientWithValidator(clientRepository, clientDataValidator()));
    }

    @Bean
    public ClientsProvider clientsProvider(ClientDataProcessor clientDataProcessor) {
        return new ClientsProviderImpl(clientDataProcessor);
    }

    @Bean
    public ClientService clientService() {
        return new ClientServiceImpl(
                Long.valueOf(environment.getRequiredProperty("client.service.time.normal-vip")),
                Long.valueOf(environment.getRequiredProperty("client.service.time.instant"))
        );
    }

    @Bean
    public PostService postService(ClientRepositoryDb clientRepository, ClientsProvider clientsProvider,
                                   CreateClientDtoValidator createClientDtoValidator) {
        return new PostServiceImpl(
                clientsProvider,
                createClientDtoValidator,
                clientRepository,
                applicationEventPublisher
        );
    }
}
