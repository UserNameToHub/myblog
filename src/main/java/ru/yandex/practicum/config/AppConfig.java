package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.dao.CommentDao;
import ru.yandex.practicum.dao.impl.CommentDaoImpl;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.impl.CommentServiceImpl;

@RequiredArgsConstructor
@Configuration
public class AppConfig {
    private final JdbcTemplate jdbcTemplate;
    private final CommentMapper commentMapper;
    @Bean
    CommentDao commentDao() {
        return new CommentDaoImpl(jdbcTemplate, commentMapper);
    }

    @Bean
    CommentService commentService() {
        return new CommentServiceImpl(commentDao());
    }
}