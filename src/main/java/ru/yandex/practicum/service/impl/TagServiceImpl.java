package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.TagDao;
import ru.yandex.practicum.model.Tag;
import ru.yandex.practicum.service.TagService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Override
    public List<Tag> getAllTagByPostId(Long postId) {
        log.info("Request to get all tags for a post with an post_id::{}", postId);
        return tagDao.getAllTagByPostId(postId);
    }

    @Override
    public void delete(Long id, Long post_id) {
        tagDao.delete(id, post_id);
    }
}