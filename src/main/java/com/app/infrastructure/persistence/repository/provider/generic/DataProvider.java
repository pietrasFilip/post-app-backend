package com.app.infrastructure.persistence.repository.provider.generic;

import java.util.List;

public interface DataProvider <T> {
    List<T> provide();
}
