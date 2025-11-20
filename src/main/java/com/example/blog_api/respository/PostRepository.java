package com.example.blog_api.respository;

import com.example.blog_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Post repository.
 *
 * Common queries:
 * - findBySlug: SEO-friendly post detail page
 * - findByCategoryId: list posts by category
 * - findByAuthorId: list posts written by an author
 * - searchByTitleContaining: simple keyword search feature
 */

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findBySlug(String slug);

    List<Post> findByCategoryId(Long categoryId);

    List<Post> findByAuthorId(Long authorId);

    List<Post> findByTitleContainingIgnoreCase(String keyword);

}
