package com.example.blog_api.dto;

/**
 * CommentCreateDTO
 * Used for adding comments under posts.
 */
public class CommentCreateDTO {
    // no CommentID
    private Long postId;
    private Long authorId;
    private String content;

    public CommentCreateDTO() {}

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
