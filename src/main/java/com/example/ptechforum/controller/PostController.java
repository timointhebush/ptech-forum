package com.example.ptechforum.controller;

import com.example.ptechforum.model.Member;
import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.helper.CurrentUser;
import com.example.ptechforum.model.vo.PostSaveRequestVo;
import com.example.ptechforum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public String index(Model model, @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> postPage = postService.findAll(pageable);
        model.addAttribute("page", postPage);
        this.activateNav(model);
        return "app/posts/index";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        PostSaveRequestVo post = new PostSaveRequestVo();
        model.addAttribute("post", post);
        this.activateNav(model);
        return "app/posts/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute PostSaveRequestVo vo, @CurrentUser Member currentMember) throws IOException {
        Post savedPost = postService.save(vo, currentMember);
        return "redirect:/posts/" + savedPost.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        this.activateNav(model);
        return "app/posts/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        this.activateNav(model);
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

    public void activateNav(Model model) {
        model.addAttribute("navActive", "posts");
    }
}
