package com.example.blog_api.service.impl;

import com.example.blog_api.dto.PostCreateDTO;
import com.example.blog_api.dto.PostDTO;
import com.example.blog_api.dto.PostUpdateDTO;

import java.util.List;

public interface PostService {

    PostDTO create(PostCreateDTO dto);

    PostDTO update(Long id, PostUpdateDTO dto);

    void delete(Long id);

    PostDTO getById(Long id);

    PostDTO getBySlug(String slug);

    List<PostDTO> getAll();

    List<PostDTO> getAllByAuthor(Long authorId);

    List<PostDTO> getAllByCategory(Long categoryId);

    List<PostDTO> searchByTitle(String keyword);
}
