package com.app.domain.clients_management.policy.factory.validator;

public interface DataValidator<T> {
    T validate(T t);

    static String validateMatchesRegex(String model, String regex) {
        if (model == null || model.isEmpty()) {
            throw new IllegalStateException("Model is null or empty");
        }

        if (!model.matches(regex)) {
            throw new IllegalStateException("Wrong input format!");
        }
        return model;
    }

    static <T> T validateNull(T t) {
        if (t == null) {
            throw new IllegalStateException("Is null");
        }
        return t;
    }
}
