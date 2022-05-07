package com.example.ptechforum.controller;

import com.example.ptechforum.model.Comment;
import com.example.ptechforum.model.vo.CommentSaveRequestVo;
import com.example.ptechforum.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public String create(@ModelAttribute CommentSaveRequestVo vo) {
        Comment savedComment = commentService.save(vo);
        return "redirect:/posts/" + savedComment.getPost().getId();
    }
}
