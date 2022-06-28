package com.example.ptechforum.controller;

import com.example.ptechforum.model.Comment;
import com.example.ptechforum.model.Member;
import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.dto.CommentDto;
import com.example.ptechforum.model.helper.CurrentUser;
import com.example.ptechforum.model.vo.CommentVo;
import com.example.ptechforum.service.CommentService;
import com.example.ptechforum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("")
    @ResponseBody
    public CommentDto create(CommentVo vo, @CurrentUser Member currentMember) {
        Post post = postService.findById(vo.getPostId());
        Comment newComment = new Comment(vo);
        newComment.assignMember(currentMember);
        newComment.assignPost(post);
        return new CommentDto(commentService.save(newComment));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        Comment deletedComment = commentService.deleteById(id);
        return "redirect:/posts/" + deletedComment.getPost().getId();
    }
}
