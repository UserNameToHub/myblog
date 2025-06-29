package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Comment;

import java.util.List;

public interface CommentService {
    void create(Long postId, String text);

    List<Comment> getAll(Long postId);

    void update(Long id, Long post_id, String text);

    void delete(Long id, Long postId);
}