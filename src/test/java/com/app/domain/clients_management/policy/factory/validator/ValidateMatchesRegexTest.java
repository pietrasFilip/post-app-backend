package com.app.domain.clients_management.policy.factory.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.domain.clients_management.policy.factory.validator.DataValidator.validateMatchesRegex;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateMatchesRegexTest {
    @Test
    @DisplayName("When matches regex")
    void test1() {
        var model = "ABC";
        assertThat(validateMatchesRegex(model, "[A-Z ]+"))
                .isEqualTo(model);
    }

    @Test
    @DisplayName("When does not matche regex")
    void test2() {
        var model = "ABC";
        assertThatThrownBy(() -> validateMatchesRegex(model, "\\d+"))
                .hasMessage("Wrong input format!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("When model is empty")
    void test3() {
        var model = "";
        assertThatThrownBy(() -> validateMatchesRegex(model, "\\d+"))
                .hasMessage("Model is null or empty")
                .isInstanceOf(IllegalStateException.class);
    }
}
