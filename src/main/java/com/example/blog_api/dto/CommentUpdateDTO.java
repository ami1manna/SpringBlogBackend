package com.example.blog_api.dto;

/**
 * CommentUpdateDTO
 * Used when updating comment content.
 */
public class CommentUpdateDTO {

    private String content;

    public CommentUpdateDTO() {}

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
