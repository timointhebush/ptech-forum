package com.example.ptechforum.controller;

import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.vo.PostSaveRequestVo;
import com.example.ptechforum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public String index(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "app/posts/index";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        PostSaveRequestVo post = new PostSaveRequestVo();
        model.addAttribute("post", post);
        return "app/posts/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute PostSaveRequestVo vo) throws IOException {
        Post savedPost = postService.save(vo);
        return "redirect:/posts/" + savedPost.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "app/posts/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "app/posts/new";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute PostSaveRequestVo vo) throws IOException {
        Post updatedPost = postService.update(vo);
        return "redirect:/posts/" + updatedPost.getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
