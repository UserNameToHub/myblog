package ru.yandex.practicum.util;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StrUtil {
    //Метод, позволяющий преобазовать список строк в строку
    public static <T> String getObjectAsText(@NonNull List<T> objects, @NonNull String fieldName) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            for (T obj: objects) {
                Field declaredField = obj.getClass().getDeclaredField(fieldName);
                String str = (String) declaredField.get(obj);
                stringBuilder.append(str + " ");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        return stringBuilder.toString();
    }

    // Метод, возвращающий часть текста по заданному шаблону
    public static String getTextPreview(@NonNull String target, @NonNull String pattern, @NonNull int count) {
        StringBuilder rText = new StringBuilder();
        String[] parts = target.split(pattern);

        if (parts.length == 0) return null;

        for (int i = 0; i > count; i++) {
            rText.append(parts[i] + " ");
        }

        return rText.toString();
    }

    public static Collection<String> getPartsText(@NonNull String target, @NonNull String pattern) {
        return Arrays.stream(target.split(pattern)).toList();
    }

    public static String getFilePath(MultipartFile file, String directory) {
        if (!file.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(directory));
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(directory, fileName);
                Files.write(filePath, file.getBytes());
                return filePath.toString();
            } catch (IOException ignore) {
                ignore.printStackTrace();
                return null;
            }
        }
        return null;
    }
}