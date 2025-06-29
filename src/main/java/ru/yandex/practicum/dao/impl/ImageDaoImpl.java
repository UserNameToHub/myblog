package ru.yandex.practicum.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dao.ImageDao;
import ru.yandex.practicum.dao.PostDao;
import ru.yandex.practicum.util.InOutUtil;

@Repository
@RequiredArgsConstructor
public class ImageDaoImpl implements ImageDao {
    private final PostDao postDao;

    @Override
    public byte[] getImages(String path) {
        return InOutUtil.getBytes(path);
    }
}
