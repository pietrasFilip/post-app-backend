package com.app.domain.clients_management.policy.factory.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.domain.clients_management.policy.factory.validator.DataValidator.validateNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateNullTest {

    @Test
    @DisplayName("When is null")
    void test1() {
        assertThatThrownBy(() -> validateNull(null))
                .hasMessage("Is null")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("When is not null")
    void test2() {
        var model = "TEST";
        assertThat(validateNull(model))
                .isEqualTo(model);
    }
}
