package com.example.ptechforum.service;

import com.example.ptechforum.model.File;
import com.example.ptechforum.model.Member;
import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.vo.PostSaveRequestVo;
import com.example.ptechforum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final FileService fileService;

    @Transactional
    public Post save(PostSaveRequestVo vo) throws IOException {
        Member member = memberService.getLoggedInMember();
        Post post = Post.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .member(member).build();
        post = postRepository.save(post);
        if (!vo.getFile().isEmpty()) {
            fileService.saveAttachment(vo.getFile(), post);
        }
        return post;
    }
}
