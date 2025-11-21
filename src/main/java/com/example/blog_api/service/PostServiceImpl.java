package com.example.blog_api.service;

import com.example.blog_api.dto.post.PostCreateDTO;
import com.example.blog_api.dto.post.PostDTO;
import com.example.blog_api.dto.post.PostUpdateDTO;
import com.example.blog_api.entity.Category;
import com.example.blog_api.entity.Post;
import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.mapper.PostMapper;
import com.example.blog_api.respository.CategoryRepository;
import com.example.blog_api.respository.PostRepository;
import com.example.blog_api.respository.UserRepository;
import com.example.blog_api.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    @Autowired
    public PostServiceImpl(PostRepository postRepo,
                           UserRepository userRepo,
                           CategoryRepository categoryRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public PostDTO create(PostCreateDTO dto) {
        // get author details
        User author = userRepo.findById(dto.getAuthorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found: " + dto.getAuthorId()));

        // get Category details
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found: " + dto.getCategoryId()));

        Post post = new Post(author, category, dto.getTitle(), dto.getSlug(), dto.getContent());
        post = postRepo.save(post);

        return PostMapper.toDTO(post);
    }

    @Override
    public PostDTO update(Long id, PostUpdateDTO dto) {

        Post post = postRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found: " + id));

        // Update fields
        post.setTitle(dto.getTitle()); // title
        post.setSlug(dto.getSlug());  //  slug
        post.setContent(dto.getContent()); // content
        // category
        if (dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Category not found: " + dto.getCategoryId()));
            post.setCategory(category);
        }

        post = postRepo.save(post);

        return PostMapper.toDTO(post);
    }

    @Override
    public void delete(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found: " + id));

        postRepo.delete(post);
    }

    @Override
    public PostDTO getById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found: " + id));

        return PostMapper.toDTO(post);
    }

    @Override
    public PostDTO getBySlug(String slug) {
        Post post = postRepo.findBySlug(slug)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found: " + slug));

        return PostMapper.toDTO(post);
    }

    @Override
    public List<PostDTO> getAll() {
        return postRepo.findAll()
                .stream()
                .map(PostMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getAllByAuthor(Long authorId) {
        return postRepo.findByAuthorId(authorId)
                .stream()
                .map(PostMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getAllByCategory(Long categoryId) {
        return postRepo.findByCategoryId(categoryId)
                .stream()
                .map(PostMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchByTitle(String keyword) {
        return postRepo.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(PostMapper::toDTO)
                .collect(Collectors.toList());
    }
}