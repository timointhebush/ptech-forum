package com.example.ptechforum.service;

import com.example.ptechforum.model.Post;
import com.example.ptechforum.model.dto.PostDto;
import com.example.ptechforum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileService fileService;

    @Transactional
    public Post save(Post post) throws IOException {
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.orElse(null);
    }

    @Transactional
    public Post deleteById(Long id) {
        Post post = this.findById(id);
        if (!Objects.isNull(post.getFile())) {
            fileService.deleteFileById(post.getFile().getId());
        }
        postRepository.delete(post);
        return post;
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAllWithMemberAndFile(pageable);
    }
}
