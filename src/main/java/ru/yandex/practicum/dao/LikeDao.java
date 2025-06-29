package ru.yandex.practicum.dao;

public interface LikeDao {
    void create(Long post_id);

    Long getCountLikes(Long postId);

    void deleteById(Long postId);
}
