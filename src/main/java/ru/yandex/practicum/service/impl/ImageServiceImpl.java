package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.ImageDao;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.ImageService;
import ru.yandex.practicum.service.PostService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageDao imageDao;

    private final PostService postService;

    @Override
    public byte[] getImage(Long id) {
        Post post = postService.getById(id);

        log.info("Get image with post");
        return imageDao.getImages(post.getImagePath());
    }
}