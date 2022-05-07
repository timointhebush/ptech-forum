package com.example.ptechforum.service;

import com.example.ptechforum.model.Comment;
import com.example.ptechforum.model.Member;
import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.vo.CommentSaveRequestVo;
import com.example.ptechforum.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    @Transactional
    public Comment save(CommentSaveRequestVo vo) {
        Member member = memberService.getLoggedInMember();
        Post post = postService.findById(vo.getPostId());
        Comment comment = new Comment(vo);
        comment.assignMember(member);
        comment.assignPost(post);
        return commentRepository.save(comment);
    }
}
