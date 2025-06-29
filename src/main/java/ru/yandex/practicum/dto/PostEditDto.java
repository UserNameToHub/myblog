package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PostEditDto {
    private Long id;

    private String title;

    private String text;

    private MultipartFile image;

    private String tags;
}