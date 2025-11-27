package com.example.blog_api.service.impl;

import com.example.blog_api.dto.post.PostCreateDTO;
import com.example.blog_api.dto.post.PostDTO;
import com.example.blog_api.dto.post.PostUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostDTO create(PostCreateDTO dto);

    PostDTO update(Long id, PostUpdateDTO dto);

    void delete(Long id);

    PostDTO getById(Long id);

    PostDTO getBySlug(String slug);

    Page<PostDTO> getAll(int page , int size);

    Page<PostDTO> getAllByAuthor(Long authorId, int page, int size);
    Page<PostDTO> getAllByCategory(Long categoryId, int page, int size);
    Page<PostDTO> searchByTitle(String keyword, int page, int size);

    Page<PostDTO> getMyPosts(int page, int size);

    PostDTO uploadImage(Long postID , MultipartFile file);
    byte[] getImageBytes(Long postId);
    void deleteImage(Long postId);



}
