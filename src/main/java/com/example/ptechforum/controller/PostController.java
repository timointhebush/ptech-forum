package com.example.ptechforum.controller;

import com.example.ptechforum.model.Member;
import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.helper.CurrentUser;
import com.example.ptechforum.model.vo.PostVo;
import com.example.ptechforum.service.FileService;
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
    private final FileService fileService;

    @GetMapping("")
    public String index(Model model, @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> postPage = postService.findAll(pageable);
        model.addAttribute("page", postPage);
        this.activateNav(model);
        return "app/posts/index";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        PostVo post = new PostVo();
        model.addAttribute("post", post);
        this.activateNav(model);
        return "app/posts/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute PostVo vo, @CurrentUser Member currentMember) throws IOException {
        Post post = Post.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .member(currentMember).build();
        postService.save(post);
        if (vo.hasFile()) {
            fileService.saveAttachment(vo.getFile(), post);
        }
        return "redirect:/posts/" + post.getId();
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
    public String update(@PathVariable("id") Long id, @ModelAttribute PostVo vo) throws IOException {
        Post postForUpdate = postService.findById(id);
        postForUpdate.update(vo);
        postService.save(postForUpdate);
        fileService.updateAttachment(postForUpdate, vo.getDeleteFileIds(), vo.getFile());
        return "redirect:/posts/" + postForUpdate.getId();
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
