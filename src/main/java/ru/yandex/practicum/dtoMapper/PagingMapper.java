package ru.yandex.practicum.dtoMapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.PagingDto;

@Component
public class PagingMapper {
    public PagingDto toPagingDto(int pageNumber, int pageSize, int totalSize) {
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, totalSize);

        boolean hasNext = end < totalSize;
        boolean hasPrevious = start > 0;

        return PagingDto.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .build();
    }
}