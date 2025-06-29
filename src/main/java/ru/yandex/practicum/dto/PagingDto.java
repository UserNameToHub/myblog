package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingDto {
    private int pageNumber;

    private int pageSize;

    private boolean hasNext;

    private boolean hasPrevious;
}