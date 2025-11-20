package com.example.blog_api.service.impl;

import com.example.blog_api.dto.CommentCreateDTO;
import com.example.blog_api.dto.CommentDTO;
import com.example.blog_api.dto.CommentUpdateDTO;

import java.util.List;

public interface CommentService {
    CommentDTO create(CommentCreateDTO dto);

    CommentDTO update(Long id, CommentUpdateDTO dto);

    void delete(Long id);

    CommentDTO getById(Long id);

    List<CommentDTO> getAllCommentsForPost(Long postId);
}
