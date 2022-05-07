package com.example.ptechforum.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveRequestVo {

    private Long id;

    private String content;

    private Long postId;
}
