package com.example.ptechforum.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestVo {

    private String title;

    private String content;

    private MultipartFile file;
}
