package com.example.blog_api.dto.post;

/**
 * PostUpdateDTO
 * Used for updating only editable fields of posts.
 */
public class PostUpdateDTO {

    private Long categoryId;
    private String title;
    private String slug;
    private String content;

    public PostUpdateDTO() {}

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
