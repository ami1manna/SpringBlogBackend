package com.example.blog_api.dto.post;

import com.example.blog_api.dto.category.CategoryDTO;
import com.example.blog_api.dto.user.UserDTO;

import java.time.LocalDateTime;

public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private LocalDateTime createdAt;

    private UserDTO user;
    private CategoryDTO category;

    public PostDTO() {}

    public PostDTO(Long id, String title, String content, String slug, LocalDateTime createdAt, UserDTO user, CategoryDTO category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.slug = slug;
        this.createdAt = createdAt;
        this.user = user;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
