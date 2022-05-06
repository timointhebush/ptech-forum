package com.example.ptechforum.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String originalName;

    private BigInteger size;

    private String extension;

    private String relativePath;

    private Integer sequence;

    private String fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Builder
    public File(String name, String originalName, BigInteger size, String extension, String relativePath, Integer sequence, String fileType, Post post) {
        this.name = name;
        this.originalName = originalName;
        this.size = size;
        this.extension = extension;
        this.relativePath = relativePath;
        this.sequence = sequence;
        this.fileType = fileType;
        this.post = post;
    }

    public void assignPost(Post post) {
        this.post = post;
    }
}
