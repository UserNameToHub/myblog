package ru.yandex.practicum.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dao.LikeDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(Long postId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("likes")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("post_id", postId);

        simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public Long getCountLikes(Long postId) {
        String sql = "select count(*) from likes " +
                "where post_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{postId}, Long.class);
    }

    @Override
    public void deleteById(Long postId) {
        Long deletedId = getByPostIdLimit1(postId);
        if (Objects.isNull(deletedId)) return;

        String sql = "delete from likes where id = ?;";
        jdbcTemplate.update(sql, deletedId);
    }

    private Long getByPostIdLimit1(Long postId) {
        String sql = "select id from likes " +
                "where post_id = ? " +
                "limit 1";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{postId}, Long.class);
        } catch (DataAccessException ignore) {
            return null;
        }
    }
}