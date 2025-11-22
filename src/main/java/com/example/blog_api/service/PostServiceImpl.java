package com.example.blog_api.service;

import com.example.blog_api.dto.post.PostCreateDTO;
import com.example.blog_api.dto.post.PostDTO;
import com.example.blog_api.dto.post.PostUpdateDTO;
import com.example.blog_api.entity.Category;
import com.example.blog_api.entity.Post;
import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ApiException;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.mapper.PostMapper;
import com.example.blog_api.respository.CategoryRepository;
import com.example.blog_api.respository.PostRepository;
import com.example.blog_api.respository.UserRepository;
import com.example.blog_api.security.AuthUtil;
import com.example.blog_api.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        User user = AuthUtil.getLoggedInUser(userRepo);

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Post post = new Post();
        post.setAuthor(user);
        post.setCategory(category);
        post.setTitle(dto.getTitle());
        post.setSlug(dto.getSlug());
        post.setContent(dto.getContent());

        Post saved = postRepo.save(post);

        return PostMapper.toDTO(saved);
    }

    @Override
    public PostDTO update(Long id, PostUpdateDTO dto) {

        Post post = postRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found: " + id));

        User user = AuthUtil.getLoggedInUser(userRepo);

        // Admin bypass
        boolean isAdmin = user.getRole().equals("ROLE_ADMIN");

        if (!isAdmin && !post.getAuthor().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to update this post");
        }


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
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));

        User user = AuthUtil.getLoggedInUser(userRepo);

        boolean isAdmin = AuthUtil.isAdmin(user);

        if (!isAdmin && !post.getAuthor().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to delete this post");
        }

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
    public Page<PostDTO> getAll(int page , int size) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
        Page<Post> posts = postRepo.findAll(pageable);

        // map entity page to dto page
        Page<PostDTO> dtoPage = posts.map(PostMapper::toDTO);
        return dtoPage;
    }

    @Override
        public Page<PostDTO> getAllByCategory(Long categoryId, int page, int size) {
            Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
            Page<Post> posts = postRepo.findByCategoryId(categoryId, pageable);
            return posts.map(PostMapper::toDTO);
        }


        @Override
    public Page<PostDTO> getAllByAuthor(Long authorId , int page , int size) {
            Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));

        return postRepo.findByAuthorId(authorId , pageable).map(PostMapper::toDTO);


    }

    @Override
    public Page<PostDTO> searchByTitle(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
        Page<Post> posts = postRepo.findByTitleContainingIgnoreCase(keyword, pageable);
        return posts.map(PostMapper::toDTO);
    }
    @Override
    public Page<PostDTO> getMyPosts(int page, int size) {
        User current = AuthUtil.getLoggedInUser(userRepo);
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
        Page<Post> posts = postRepo.findByAuthorId(current.getId(), pageable);
        return posts.map(PostMapper::toDTO);
    }

}