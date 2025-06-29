package ru.yandex.practicum.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {
    private Long id;

    private Byte image;
}