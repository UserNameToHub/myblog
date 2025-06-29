package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.LikeDao;
import ru.yandex.practicum.service.LikeService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeDao likeDao;

    @Override
    public void create(Long post_id) {
        log.info("Create new like");
        likeDao.create(post_id);
    }

    @Override
    public Long getCountLikes(Long postId) {
        log.info("Get countLikes");
        return likeDao.getCountLikes(postId);
    }

    @Override
    public void deleteById(Long postId) {
        log.info("Delete like");
        likeDao.deleteById(postId);
    }
}