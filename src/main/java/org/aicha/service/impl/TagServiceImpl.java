package org.aicha.service.impl;

import org.aicha.model.Tag;
import org.aicha.model.Task;

import java.util.List;
import java.util.Optional;

public interface TagServiceImpl {
    List<Tag> findAll();
    Optional<Tag> findById(Long id);
}
