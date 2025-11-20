package com.example.blog_api.mapper;


import com.example.blog_api.dto.CategoryDTO;
import com.example.blog_api.dto.PostDTO;
import com.example.blog_api.dto.UserDTO;
import com.example.blog_api.entity.Post;

/**
 * Mapper for Post entity.
 * Converts Post -> PostDTO with nested user & category.
 */
public class PostMapper {

    public static PostDTO toDTO(Post post) {
        if (post == null) return null;

        // Nested DTO created using UserMapper & CategoryMapper
        UserDTO author = UserMapper.toDTO(post.getAuthor());
        CategoryDTO category = CategoryMapper.toDTO(post.getCategory());

        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getContent(),
                post.getCreatedAt(),
                author,
                category
        );
    }
}
