package ru.yandex.practicum.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.PostService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Comment comment;

    private static Post post;

    private static PostCreateDto postCreateDto;

    private static MultipartFile multipartFile;

    @BeforeAll
    static void setUp() {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/image.jpg"));
        } catch (IOException ignore) {

        }

        multipartFile = new MockMultipartFile("linux", "image.peg", "image/jpeg", "Image".getBytes());

        comment = Comment.builder()
                .text("Спасибо! Очень полезный пост.")
                .build();

        postCreateDto = PostCreateDto.builder()
                .title("Title")
                .text("text")
                .image(multipartFile)
                .tags("new")
                .build();
    }


    @Test
    void create() {
        Long id = createPost();
        Post postWithComment = postService.getById(id);
        assertThat(postWithComment.getComments(), not(empty()));
    }

    @Test
    void getAll() {
        Long id = createPost();
        List<Comment> comments = commentService.getAll(id);

        assertThat(comments, not(empty()));
    }

    @Test
    void update() {
        Long post_id = createPost();
        Comment comment = commentService.getAll(post_id).get(0);
        String newText = String.format("Новый текст комментария к посту, id которого = %d", post_id);
        commentService.update(comment.getId(), post_id, newText);

        Comment updatedComment = commentService.getAll(post_id).get(0);

        assertThat(updatedComment.getText(), equalTo(newText));
    }

    private Long createPost() {
        Post post = postService.create(postCreateDto);
        Long id = post.getId();
        String text = String.format("Первый комментарий к посту, id которого = %d", id);
        commentService.create(id, text);
        return id;
    }
}