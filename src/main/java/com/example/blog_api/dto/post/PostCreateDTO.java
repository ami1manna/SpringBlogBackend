package com.example.blog_api.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PostCreateDTO
 * Used for creating new posts from client.
 */
@Schema(description = "DTO used to create a new blog post")
public class PostCreateDTO {


     @Schema(description = "ID of the category", example = "2")
     private Long categoryId;

     @Schema(description = "Post title", example = "Top 10 Java Tips")
     private String title;

     @Schema(description = "URL friendly slug", example = "top-10-java-tips")
     private String slug;

     @Schema(description = "Main content of the post", example = "Full article content...")
     private String content;

    public PostCreateDTO() {}


    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

