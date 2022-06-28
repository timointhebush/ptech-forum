package com.example.ptechforum.service;

import com.example.ptechforum.model.Comment;
import com.example.ptechforum.model.Member;
import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.dto.CommentDto;
import com.example.ptechforum.model.vo.CommentVo;
import com.example.ptechforum.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional
    public Comment deleteById(Long id) {
        Comment deletedComment = this.findById(id);
        commentRepository.delete(deletedComment);
        return deletedComment;
    }

    public Page<CommentDto> findAllByPostId(Pageable pageable, Long postId) {
        Page<Comment> commentPage = commentRepository.findAllByPostId(pageable, postId);
        return commentPage.map(CommentDto::new);
    }
}
