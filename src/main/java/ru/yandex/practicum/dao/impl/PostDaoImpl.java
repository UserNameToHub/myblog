package ru.yandex.practicum.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dao.PostDao;
import ru.yandex.practicum.dao.TagDao;
import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.dto.PostEditDto;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.Tag;
import ru.yandex.practicum.util.Constants;
import ru.yandex.practicum.util.StrUtil;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao {
    private final JdbcTemplate jdbcTemplate;

    private final PostMapper postMapper;

    private final TagDao tagDao;

    @Override
    public Post create(PostCreateDto type) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("posts")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("TITLE", type.getTitle());
        params.put("TEXT", type.getText());
        params.put("IMAGE_PATH", StrUtil.getFilePath(type.getImage(), Constants.UPLOAD_DIR));

        long generatedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        tagDao.saveAll(type.getTags(), generatedId);

        return get(generatedId).get();
    }

    @Override
    public Optional<Post> get(Long id) {
        String selectQuery = "select * from posts where id = ?";
        List<Post> foundPost = jdbcTemplate.query(selectQuery, postMapper::mapRow, id);

        return foundPost.isEmpty() ? Optional.empty() : Optional.of(foundPost.getFirst());
    }

    @Override
    public Collection<Post> getAll(String search, int pageNumber, int pageSize) {
        boolean isSearch = (search != null && !search.trim().isEmpty());
        String sql = !isSearch ? "select * from posts limit ? offset ?" : "select p.* from posts_tags pt " +
                "join tags t on pt.tag_id = t.id " +
                "join posts p on pt.post_id = p.id " +
                "where t.tag_value = ? " +
                "LIMIT ? OFFSET ?";;
        Object[] params = isSearch ? new Object[]{search, pageSize, (pageNumber - 1) * pageSize} :
                new Object[]{pageSize, (pageNumber - 1) * pageSize};

            Collection<Post> res = jdbcTemplate.query(sql, params, postMapper::mapRow);
            return res;
    }

    @Override
    public Optional<Post> update(PostEditDto type) {
        if (!isExists(type.getId())) {
            log.warn("Post with ID {} not found", type.getId());
            return Optional.empty();
        }

         boolean isNull = Objects.isNull(type.getImage());

        String updateQuery = isNull ? "update posts set title = ?, text = ? where id = ?" :
                "update posts set title = ?, text = ?, image_path = ? where id = ?";

        Object[] param = isNull ? new Object[] {type.getTitle(), type.getText(), type.getId()} :
                new Object[]{type.getTitle(),  type.getText(), StrUtil.getFilePath(type.getImage(), Constants.UPLOAD_DIR), type.getId()};

        jdbcTemplate.update(updateQuery, param);

        if (!type.getTags().trim().isEmpty()) {
            List<Tag> allTags = tagDao.getAllTagByPostId(type.getId());
            allTags.stream().forEach(tag -> {
                tagDao.delete(type.getId(), tag.getId());
            });

            tagDao.saveAll(type.getTags(), type.getId());
        }

        return this.get(type.getId());
    }

    @Override
    public void delete(Long id) {
        if (isExists(id)) log.warn("Post with ID {} not found", id);

        String deleteQuery = "delete from posts where id = ?";
        jdbcTemplate.update(deleteQuery, id);
    }

    public boolean isExists(Long id) {
        return jdbcTemplate.queryForObject("select exists(select 1 from posts where id = ?)",
                Boolean.class, id);
    }
}