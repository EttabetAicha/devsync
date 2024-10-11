package org.aicha.service;

import org.aicha.model.Tag;
import org.aicha.repository.TagRepository;
import org.aicha.service.impl.TagServiceImpl;

import java.util.List;
import java.util.Optional;

public class TagService implements TagServiceImpl {
    TagRepository tagRepository;
    public TagService() {
        tagRepository = new TagRepository();
    }
    public List<Tag> findAll(){
        return tagRepository.findAll();
    }
    public Optional<Tag> findById(Long id){
        return tagRepository.findById(id);
    }
}