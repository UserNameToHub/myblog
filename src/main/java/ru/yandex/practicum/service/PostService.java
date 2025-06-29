package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.dto.PostEditDto;
import ru.yandex.practicum.model.Post;

import java.util.Collection;

public interface PostService {
    Post create(PostCreateDto postCreateDto);

    Post getById(Long id);

    Collection<Post> getAll(String search,int pageNumber, int pageSize);

    Post update(PostEditDto postEditDto);

    void deleteById(Long id);
}