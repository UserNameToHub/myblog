package ru.yandex.practicum.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Post {
    private Long id;

    private String title;

    private String text;

    private String imagePath;

    private List<Tag> tags;

    private Long likeComment;

    private List<Comment> comments;

    public String getTagsAsText() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Tag tag : tags) {
            stringBuilder.append(tag.getValue() + " ");
        }

        return stringBuilder.toString();
    }

    public String getTextPreview() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] parts = text.split("\\.");

        return null;
    }
}
