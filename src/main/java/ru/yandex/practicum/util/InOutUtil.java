package ru.yandex.practicum.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InOutUtil {
    public static byte[] getBytes(String imagePath) {
        try {
            return Files.readAllBytes(Paths.get(imagePath));
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }
    }
}