package com.example.ptechforum.model.dto;

import com.example.ptechforum.model.Comment;
import lombok.Getter;

@Getter
public class CommentDto {
    private Long id;

    private String content;

    private MemberDto member;

    private String createdAt;

    private String updatedAt;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.member = new MemberDto(comment.getMember());
        this.createdAt = String.valueOf(comment.getCreatedAt());
        this.updatedAt = String.valueOf(comment.getUpdatedAt());
    }
}
