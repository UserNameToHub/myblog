package ru.yandex.practicum.service;

public interface LikeService {
    void create(Long post_id);

    Long getCountLikes(Long postId);

    void deleteById(Long postId);
}