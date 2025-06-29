package ru.yandex.practicum.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.config.DataConfig;
import ru.yandex.practicum.configuration.TestConfig;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataConfig.class, TestConfig.class})
@WebMvcTest(controllers = PostController.class)
class PostControllerTest {
    @MockitoBean
    private PostService postService;
    private static Post post;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setUp() {
         post = Post.builder()
                 .id(12l)
                 .text("some text")
                 .title("some title")
                 .tags(new ArrayList<>())
                 .comments(new ArrayList<>())
                 .likeComment(123l)
                 .imagePath("url")
                 .build();
    }

    @Test
    void testGetWhenResponseIsOk() throws Exception {
        when(postService.getAll(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(post));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts"));

    }

    @Test
    void getById() throws Exception {
        when(postService.getById(anyLong()))
                .thenReturn(post);

        mockMvc.perform(get("/posts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("tags"));
    }

    @Test
    void edit() throws Exception {
        when(postService.getById(anyLong()))
                .thenReturn(post);

        when(StrUtil.getObjectAsText(any(), anyString()))
                .thenReturn("Some text");

        mockMvc.perform(get("posts/{id}/edit", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("tagStr"));
    }
}