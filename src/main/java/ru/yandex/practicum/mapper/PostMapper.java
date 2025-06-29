package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dao.CommentDao;
import ru.yandex.practicum.dao.LikeDao;
import ru.yandex.practicum.dao.TagDao;
import ru.yandex.practicum.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class PostMapper implements RowMapper<Post> {
    private final LikeDao likeDao;
    private final CommentDao commentDao;
    private final TagDao tagDao;

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Post.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .text(rs.getString("text"))
                .imagePath(rs.getString("image_Path"))
                .tags(tagDao.getAllTagByPostId(rs.getLong("id")))
                .likeComment(likeDao.getCountLikes(rs.getLong("id")))
                .comments(commentDao.getAll(rs.getLong("id")))
                .build();
    }
}