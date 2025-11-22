package com.example.blog_api.respository;

import com.example.blog_api.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Comment repository.
 *
 * Useful queries:
 * - findByPostId: list comments under a post
 * - findByAuthorId: optional filtering by commenter
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostId(Long postId, Pageable pageable);

    Page<Comment> findByAuthorId(Long authorId, Pageable pageable);

}
