package org.aicha.service;

import org.aicha.model.Tag;
import org.aicha.repository.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TagService {
    private TagRepository tagRepository;

    public TagService() {
        tagRepository = new TagRepository();
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        // validate id
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        return tagRepository.findById(id);
    }

    public Optional<Tag> createTag(Tag tag) {
        // validate tag
        if(tag == null || tag.getName() == null || tag.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid tag");
        }

        // validate name unique
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new IllegalArgumentException("Tag already exists");
        }

        return tagRepository.create(tag);
    }

    public Optional<Tag> updateTag(Tag tag) {
        // validate tag
        if(tag == null || tag.getName() == null || tag.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid tag");
        }
        // validate id
        if(tag.getId() == null || tag.getId() <= 0) {
            throw new IllegalArgumentException("Invalid tag id");
        }
        return tagRepository.update(tag);
    }

    public Tag deleteTag(Tag tag) {
        // validate tag
        if(tag == null || tag.getId() == null || tag.getId() <= 0) {
            throw new IllegalArgumentException("Invalid tag");
        }
        return tagRepository.delete(tag);
    }

    public List<Tag> getTagsByIds(List<Long> ids) {
        // validate ids
        if(ids == null) {
            throw new IllegalArgumentException("Invalid ids");
        }

        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .filter(tag -> ids.contains(tag.getId()))
                .collect(Collectors.toList());
    }
}
