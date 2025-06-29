package ru.yandex.practicum.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.dao.CommentDao;
import ru.yandex.practicum.dao.LikeDao;
import ru.yandex.practicum.dao.PostDao;
import ru.yandex.practicum.dao.TagDao;
import ru.yandex.practicum.dao.impl.CommentDaoImpl;
import ru.yandex.practicum.dao.impl.LikeDaoImpl;
import ru.yandex.practicum.dao.impl.PostDaoImpl;
import ru.yandex.practicum.dao.impl.TagDaoImpl;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.mapper.TagMapper;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.service.impl.CommentServiceImpl;
import ru.yandex.practicum.service.impl.PostServiceImpl;

@Configuration
@RequiredArgsConstructor
public class TestConfig {

    private final JdbcTemplate jdbcTemplateTest;

    @Bean
    public PostService postServiceTest() {
        return new PostServiceImpl(postDaoTest());
    }

   @Bean
    public PostDao postDaoTest() {
        return new PostDaoImpl(jdbcTemplateTest, postMapperTest(), tagDaoTest());
    }

    @Bean
    public PostMapper postMapperTest() {
        return new PostMapper(likeDaoTest(), commentDaoTest(), tagDaoTest());
    }

    @Bean
    public CommentMapper commentMapperTest() {
        return new CommentMapper();
    }

    @Bean
    public TagDao tagDaoTest() {
        return new TagDaoImpl(jdbcTemplateTest, tagMapperTest());
    }

    @Bean
    public TagMapper tagMapperTest() {
        return new TagMapper();
    }

    @Bean
    public LikeDao likeDaoTest() {
        return new LikeDaoImpl(jdbcTemplateTest);
    }

    @Bean
    public CommentDao commentDaoTest() {
        return new CommentDaoImpl(jdbcTemplateTest, commentMapperTest());
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceImpl(commentDaoTest());
    }
}