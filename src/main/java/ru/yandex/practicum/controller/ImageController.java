package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.dao.PostDao;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.ImageService;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final PostDao postDao;

    private final ImageService imageService;

    @GetMapping("/{id}")
    public byte[] getImages(@PathVariable("id") Long id) {
        Post post = postDao.get(id).orElseThrow(() ->
                new NotFoundException(String.format("Post with id %s not found", id)));

        return imageService.getImage(id);
    }
}