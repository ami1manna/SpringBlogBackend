package com.example.blog_api.respository;

import com.example.blog_api.entity.Comment;
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

    List<Comment> findByPostId(Long postId);

    List<Comment> findByAuthorId(Long authorId);
}
