package ru.yandex.practicum.common;

/**
 *
 * @param <T1> - type
 * @param <T2> - dto
 */

public interface BaseMapper<T1, T2> {
    T1 toType(T2 dto);
}
