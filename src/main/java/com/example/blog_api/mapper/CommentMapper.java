package com.example.blog_api.mapper;


import com.example.blog_api.dto.comment.CommentDTO;
import com.example.blog_api.entity.Comment;

/**
 * Mapper for Comment entity.
 * Converts Comment entity to CommentDTO.
 */
public class CommentMapper {

    public static CommentDTO toDTO(Comment comment) {
        if (comment == null) return null;

        return new CommentDTO(
                comment.getId(),
                comment.getPost() != null ? comment.getPost().getId() : null,
                comment.getAuthor() != null ? comment.getAuthor().getId() : null,
                comment.getAuthor() != null ? comment.getAuthor().getName() : null,
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}

