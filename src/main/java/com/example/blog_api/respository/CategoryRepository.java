package com.example.blog_api.respository;

import com.example.blog_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Category repository.
 * - findBySlug: used for SEO-friendly category pages (e.g., /api/categories/technology)
 * - existsByName: helps validate uniqueness
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
