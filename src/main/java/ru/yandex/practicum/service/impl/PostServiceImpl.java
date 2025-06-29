package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.PostDao;
import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.dto.PostEditDto;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.PostService;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDao postDao;

    @Override
    public Post create(PostCreateDto postCreateDto) {
        log.info("Create new post");
        return postDao.create(postCreateDto);
    }

    @Override
    public Post getById(Long id) {
        Post post = postDao.get(id).orElseThrow(() -> new NotFoundException("Post is not exist"));
        log.info("Get post by {}::", id);
        return post;
    }

    @Override
    public Collection<Post> getAll(String search,int pageNumber, int pageSize) {
        log.info("Request to search all posts");
        Collection all = postDao.getAll(search, pageNumber, pageSize);
        log.info("Query result:: {} posts", all.size());

        return all;
    }

    @Override
    public Post update(PostEditDto postEditDto) {
        return postDao.update(postEditDto).get();
    }

    @Override
    public void deleteById(Long id) {
        postDao.delete(id);
    }
}