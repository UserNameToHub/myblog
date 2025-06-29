package ru.yandex.practicum.dtoMapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.common.BaseMapper;
import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.model.Post;

@Component("postMapper")
public class PostMapper implements BaseMapper<Post, PostCreateDto> {
    @Override
    public Post toType(PostCreateDto dto) {
        return Post.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
    }
}