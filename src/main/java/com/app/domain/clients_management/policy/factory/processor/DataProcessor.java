package com.app.domain.clients_management.policy.factory.processor;

import java.util.List;

public interface DataProcessor<T> {
    List<T> process();
}
