package ru.yandex.practicum.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dao.TagDao;
import ru.yandex.practicum.mapper.TagMapper;
import ru.yandex.practicum.model.Tag;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;

    private final TagMapper tagMapper;

    @Override
    public Tag save(String value, Long postId) {
        String valueLow = value.trim().toLowerCase();
        Optional<Tag>  foundTag = getByValue(valueLow);
        if (foundTag.isPresent()) {
            String insertSql = "insert into posts_tags (post_id, tag_id) values (?, ?)";
            jdbcTemplate.update(insertSql, postId, foundTag.get().getId());
            return foundTag.get();
        }

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tags")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("tag_value", valueLow);

        long generatedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        Tag tag = Tag.builder()
                .id(generatedId)
                .value(valueLow)
                .build();


        String insertSql = "insert into posts_tags (post_id, tag_id) values (?, ?)";

        jdbcTemplate.update(insertSql, postId, tag.getId());

        return tag;
    }

    @Override
    public List<Tag> saveAll(String tagsStr, Long postId) {
        List<Tag> tagList = new ArrayList<>();
        String[] tags = tagsStr.split(" ");

        Arrays.stream(tags).forEach(str -> {
            tagList.add(this.save(str, postId));
        });

        return tagList;
    }

    @Override
    public Optional<Tag> getByValue(String value) {
        String selectQuery = "select * from tags where tag_value = ?";
        List<Tag> foundPost = jdbcTemplate.query(selectQuery, tagMapper::mapRow, value);

        return foundPost.isEmpty() ? Optional.empty() : Optional.of(foundPost.getFirst());
    }

    @Override
    public List<Tag> getAllTagByPostId(Long postId) {
        List<Tag> tags = new ArrayList<>();

        String LongSql = "select tag_id from posts_tags where post_id = ?";
        List<Long> tagIds = jdbcTemplate.queryForList(LongSql, new Object[]{postId}, Long.class);

        String tagSql = "select * from tags where id = ?";

        tagIds.stream().forEach(id -> {
            tags.add(jdbcTemplate.query(tagSql, tagMapper::mapRow, id).getFirst());
        });

        return tags;
    }

    @Override
    public void delete(Long id, Long post_id) {
        String deleteSql = "delete from post_tags where post_id = ? and tags_id = ?;";
        jdbcTemplate.update(deleteSql, post_id, id);
    }
}