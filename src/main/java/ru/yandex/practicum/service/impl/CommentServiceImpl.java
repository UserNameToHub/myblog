package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dao.CommentDao;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.service.CommentService;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    @Override
    public void create(Long postId, String text) {
        log.info("Create new like");
        commentDao.create(postId, text);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Long postId) {
        log.info("Get all comments");
        return commentDao.getAll(postId);
    }

    @Override
    public void update(Long id, Long post_id, String text) {
        log.info("Update comment");
        commentDao.update(id, post_id, text);
    }

    @Override
    @Transactional
    public void delete(Long id, Long postId) {
        log.info("Delete comment");
        commentDao.delete(id, postId);
    }
}