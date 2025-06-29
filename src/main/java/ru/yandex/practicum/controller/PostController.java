package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.PagingDto;
import ru.yandex.practicum.dto.PostCreateDto;
import ru.yandex.practicum.dto.PostEditDto;
import ru.yandex.practicum.dtoMapper.PagingMapper;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.LikeService;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.service.TagService;
import ru.yandex.practicum.util.StrUtil;

import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private final PagingMapper pagingMapper;

    private final TagService tagService;

    private final LikeService likeService;

    private final CommentService commentService;

    @GetMapping
    public String get() {
        return "redirect:/posts";
    }

    @GetMapping("posts")
    public String getAll(@RequestParam(value = "search", required = false, defaultValue = "") String search,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                         @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                         Model model) {

        Collection<Post> posts = postService.getAll(search, pageNumber, pageSize);
        PagingDto paging = pagingMapper.toPagingDto(pageNumber, pageSize, posts.size());

        model.addAttribute("posts", posts);
        model.addAttribute("search", search);
        model.addAttribute("paging", paging);

        return "posts";
    }

    @GetMapping("posts/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        Post post = postService.getById(id);
        model.addAttribute("post", post);
        model.addAttribute("tags", tagService.getAllTagByPostId(id));
        model.addAttribute("textParts", StrUtil.getPartsText(post.getText(), "/\n{2,}/"));

        return "post";
    }

    @GetMapping("posts/add")
    public String creat() {
        return "add-post";
    }

    @PostMapping("posts")
    public String createPost(@ModelAttribute PostCreateDto postCreateDto) {
        //TODO add id
        return "redirect:/posts/" + postService.create(postCreateDto).getId();
    }


    @PostMapping("posts/{id}/like")
    public String changeLike(@PathVariable("id") Long id, @RequestParam("like") boolean like) {
        //TODO add id
        if (like) {
            likeService.create(id);
        } else {
            likeService.deleteById(id);
        }
        return "redirect:/posts/" + id;
    }

    @PostMapping("posts/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Post post = postService.getById(id);
        String str = StrUtil.getObjectAsText(tagService.getAllTagByPostId(id), "value");

        model.addAttribute("post", post);
        model.addAttribute("tagStr", str);

        return "redirect:add-post";
    }

    @PostMapping("posts/{id}")
    public String editPost(@ModelAttribute PostEditDto postEditDto) {
        //TODO add id
        postService.update(postEditDto);
        return "redirect:/posts/" + postEditDto.getId();
    }

    @PostMapping("posts/{id}/comments")
    public String addComment(@PathVariable("id") Long id, @RequestParam("text") String text) {
        //TODO add id
        commentService.create(id, text);
        return "redirect:/posts/" + id;
    }

    @PostMapping("posts/{id}/comments/{commentId}")
    public String editComment(@PathVariable("id") Long id,
                              @PathVariable("commentId") Long commentId,
                              @RequestParam("text") String text) {
        //TODO add id
        commentService.update(commentId, id, text);
        return "redirect:/posts";
    }

    @PostMapping("posts/{id}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {
        //TODO add id
        commentService.delete(commentId, id);
        return "redirect:/posts/" + id;
    }

    @PostMapping( "posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return "redirect:/posts/" + id;
    }
}