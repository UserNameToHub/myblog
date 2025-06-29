package ru.yandex.practicum.dao;

import ru.yandex.practicum.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    Tag save(String value, Long postId);

    Optional<Tag> getByValue(String value);

    List<Tag> getAllTagByPostId(Long postId);

    List<Tag> saveAll(String tagsStr, Long postId);

    void delete(Long id, Long post_id);
}

