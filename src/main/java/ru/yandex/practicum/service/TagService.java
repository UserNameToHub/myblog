package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTagByPostId(Long postId);

    void delete(Long id, Long post_id);
}