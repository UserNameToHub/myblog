package ru.yandex.practicum.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.dto.PostEditDto;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.PostService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PostServiceImplTest {
    public PostService postService;

    private static MultipartFile multipartFile;

    private static PostCreateDto postCreateDto;

    private static PostEditDto postEditDto;

    private int textCode;

    @BeforeAll
    static void setUp() {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/image.jpg"));
        } catch (IOException ignore) {

        }

        multipartFile = new MockMultipartFile("linux", "image.peg", "image/jpeg", "Image".getBytes());

        postCreateDto = PostCreateDto.builder()
                .title("Linux")
                .text("Linux — это семейство Unix-подобных операционных систем (ОС), " +
                        "работающих на основе одноимённого ядра. Нет одной операционной системы Linux, " +
                        "как, например, Windows или MacOS. Существует множество дистрибутивов — " +
                        "наборов файлов, которые выполняют конкретные задачи.")
                .tags("os linux")
                .image(multipartFile)
                .build();

        postEditDto = PostEditDto.builder()
                .tags("linux1 os1")
                .text("Новый заголовок")
                .title("Новый текст")
                .build();

    }

    @Test
    void create() {
        Random random = new Random();
        textCode = random.nextInt(Integer.MAX_VALUE);
        postCreateDto.setTitle(postCreateDto.getTitle() + textCode);

        Post post = postService.create(postCreateDto);
        assertThat(post, notNullValue());
        assertThat(post.getTitle(), equalTo(postCreateDto.getTitle()));
        assertThat(post.getText(), equalTo(postCreateDto.getText()));
    }

    @Test
    void getById() {
        Collection<Post> posts = postService.getAll("", 3, 10);
        List<Post> postList = (List<Post>) posts;
        Post post = postList.get(0);

        Post postId = postService.getById(post.getId());

        assertThat(post.getId(), equalTo(postId.getId()));
    }

    @Test
    void getAll() {
        Collection<Post> posts = postService.getAll("", 3, 10);

        assertThat(posts, not(empty()));

        List<Post> postList = (List<Post>) posts;
        assertThat(postList.get(0).getTitle(), equalTo(postCreateDto.getTitle()));
        assertThat(postList.get(0).getText(), equalTo(postCreateDto.getText()));
    }

    @Test
    void update() {
        Collection<Post> posts = postService.getAll("", 3, 10);
        assertThat(posts, not(empty()));

        postEditDto.setId(((List<Post>) posts).get(0).getId());

        Post updatedPost = postService.update(postEditDto);
        assertThat(updatedPost.getTitle(), equalTo(postEditDto.getTitle()));
        assertThat(updatedPost.getText(), equalTo(postEditDto.getText()));
    }

    @Test
    void deleteById() {
        Collection<Post> posts = postService.getAll("", 1, 10);
        List<Post> postList = (List<Post>) posts;
        Post post = postList.get(new Random().nextInt(1, 10));

        postService.deleteById(post.getId());
        assertThrows(NotFoundException.class, () -> {
            postService.getById(post.getId());
        });
    }
}