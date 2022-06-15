package com.example.ptechforum.repository;

import com.example.ptechforum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"member", "file", "comments"})
    @Query("SELECT DISTINCT p FROM Post p WHERE p.id = :id")
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = {"member", "file"})
    @Query("SELECT p FROM Post p")
    Page<Post> findAllWithMemberAndFile(Pageable pageable);
}
