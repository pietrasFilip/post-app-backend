package com.app.application.validator.client.impl;

import com.app.application.dto.client.CreateClientDto;
import com.app.application.validator.client.CreateClientDtoValidator;
import com.app.domain.clients_management.model.priority.Priority;
import lombok.RequiredArgsConstructor;

import static com.app.application.validator.client.generic.ClientDtoValidator.validateEmptyPassword;
import static com.app.application.validator.client.generic.ClientDtoValidator.validateMatchesRegex;

@RequiredArgsConstructor
public class CreateClientDtoValidatorImpl implements CreateClientDtoValidator {
    private final String usernameRegex;
    private final String instantPassword;
    private final String vipPassword;
    @Override
    public CreateClientDto validate(CreateClientDto clientDto) {
        if (clientDto == null) {
            throw new IllegalStateException("Client is null");
        }

        return new CreateClientDto(
                validateMatchesRegex(clientDto.username(), usernameRegex),
                clientDto.priority(),
                validatePassword(clientDto.password(), clientDto.priority())
        );
    }

    private String validatePassword(String password, Priority priority) {
        return switch (priority) {
            case INSTANT -> validateMatchesRegex(password, instantPassword);
            case VIP -> validateMatchesRegex(password, vipPassword);
            case NORMAL -> validateEmptyPassword(password);
        };
    }
}
