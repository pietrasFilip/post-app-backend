package com.app.application.validator;

import com.app.application.dto.client.CreateClientDto;
import com.app.application.validator.client.impl.CreateClientDtoValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.domain.clients_management.model.priority.Priority.NORMAL;
import static com.app.domain.clients_management.model.priority.Priority.VIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateTest {

    @Test
    @DisplayName("When client data correct")
    void test1() {
        var validator = new CreateClientDtoValidatorImpl("[A-Z ]+", "0000", "8888");
        var client = new CreateClientDto("AA", NORMAL, "");

        assertThat(validator.validate(client))
                .isInstanceOf(CreateClientDto.class)
                .isEqualTo(client);
    }

    @Test
    @DisplayName("When client username does not match regex")
    void test2() {
        var validator = new CreateClientDtoValidatorImpl("[A-Z ]+", "0000", "8888");
        var client = new CreateClientDto("aa", NORMAL, "");

        assertThatThrownBy(() -> validator.validate(client))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Wrong input format!");
    }

    @Test
    @DisplayName("When client password is empty and client is VIP or INSTANT")
    void test3() {
        var validator = new CreateClientDtoValidatorImpl("[A-Z ]+", "0000", "8888");
        var client = new CreateClientDto("AA", VIP, "");

        assertThatThrownBy(() -> validator.validate(client))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Password is null or empty");
    }

    @Test
    @DisplayName("When client password is wrong and client is VIP or INSTANT")
    void test4() {
        var validator = new CreateClientDtoValidatorImpl("[A-Z ]+", "0000", "8888");
        var client = new CreateClientDto("AA", VIP, "12");

        assertThatThrownBy(() -> validator.validate(client))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Wrong input format!");
    }

    @Test
    @DisplayName("When client password is not empty and client is NORMAL")
    void test5() {
        var validator = new CreateClientDtoValidatorImpl("[A-Z ]+", "0000", "8888");
        var client = new CreateClientDto("AA", NORMAL, "12");

        assertThatThrownBy(() -> validator.validate(client))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No password required!");
    }
}
