package com.app.application.validator.client.generic;

public interface ClientDtoValidator<T> {
    T validate(T t);

    static String validateMatchesRegex(String model, String regex) {
        if (model == null || model.isEmpty()) {
            throw new IllegalStateException("Password is null or empty");
        }

        if (!model.matches(regex)) {
            throw new IllegalStateException("Wrong input format!");
        }
        return model;
    }

    static String validateEmptyPassword(String password) {
        if (!password.isEmpty()) {
            throw new IllegalStateException("No password required!");
        }
        return password;
    }
}
