package ru.yandex.practicum.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dao.CommentDao;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.model.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {
    private final JdbcTemplate jdbcTemplate;

    private final CommentMapper commentMapper;

    @Override
    public void create(Long postId, String text) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("comments")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("post_id", postId);
        params.put("text", text);

        simpleJdbcInsert.executeAndReturnKey(params).longValue();

    }

    @Override
    public List<Comment> getAll(Long postId) {
        String selectAllAql = "select * from comments where post_Id = ?";
        return jdbcTemplate.query(selectAllAql, commentMapper::mapRow, postId);
    }

    @Override
    public void update(Long id, Long postId, String text) {
        String updateSql = "update comments set text = ? " +
                "where id = ? and post_id = ?";
        jdbcTemplate.update(updateSql, text, id, postId);
    }

    @Override
    public void delete(Long id, Long postId) {
        String deleteSql = "delete from comments " +
                "where id = ? and post_id = ?;";
        jdbcTemplate.update(deleteSql, id, postId);
    }
}