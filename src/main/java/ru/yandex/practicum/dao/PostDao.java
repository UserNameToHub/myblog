package ru.yandex.practicum.dao;

import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.dto.PostEditDto;
import ru.yandex.practicum.model.Post;

import java.util.*;

public interface PostDao {
    Post create(PostCreateDto type);

    Optional<Post> get(Long id);

    Collection<Post> getAll(String search, int pageNumber, int pageSize);

    Optional<Post> update(PostEditDto postEditDto);

    void delete(Long id);

    boolean isExists(Long id);
}