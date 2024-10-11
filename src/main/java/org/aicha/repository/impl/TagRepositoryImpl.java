package org.aicha.repository.impl;

import org.aicha.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepositoryImpl {
    List<Tag> findAll();
    Optional<Tag> findById(Long id);
}
