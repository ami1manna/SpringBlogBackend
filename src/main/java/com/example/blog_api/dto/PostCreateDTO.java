package com.example.blog_api.dto;

 /**
 * PostCreateDTO
 * Used for creating new posts from client.
 */
public class PostCreateDTO {
    // except id , createdAt ,
    // userDTO(instead its ID) , CategoryID(instead its ID)
    private Long authorId;
    private Long categoryId;
    private String title;
    private String slug;
    private String content;

    public PostCreateDTO() {}

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

