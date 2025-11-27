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
import com.example.blog_api.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private  final ImageUtil imageUtil;
    @Autowired
    public PostServiceImpl(PostRepository postRepo,
                           UserRepository userRepo,
                           CategoryRepository categoryRepo,
                           ImageUtil imageUtil) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.imageUtil = imageUtil;
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

    @Override
    public PostDTO uploadImage(Long postId, MultipartFile file) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found: " + postId));

        // Only owner or admin
        User current = AuthUtil.getLoggedInUser(userRepo);
        boolean isAdmin = AuthUtil.isAdmin(current);
        if (!isAdmin && !post.getAuthor().getId().equals(current.getId())) {
            throw new ApiException("You are not allowed to update this post");
        }

        // delete existing
        if(post.getImagePath() != null){
            imageUtil.deleteImage(post.getImagePath());
        }

        // Save new image
        String path = imageUtil.saveImage(file);

        post.setImagePath(path);
//        System.out.println(post);
        postRepo.save(post);

//        System.out.println(PostMapper.toDTO(post));
        return PostMapper.toDTO(post);
    }


    @Override
    public byte[] getImageBytes(Long postId) {

        // find post
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found: " + postId));

        if (post.getImagePath() == null) {
            throw new ApiException("No image found for this post");
        }

        return imageUtil.getImage(post.getImagePath());

    }

    @Override
    public void deleteImage(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // Only owner or admin
        User current = AuthUtil.getLoggedInUser(userRepo);
        boolean isAdmin = AuthUtil.isAdmin(current);
        if (!isAdmin && !post.getAuthor().getId().equals(current.getId())) {
            throw new ApiException("Not allowed to delete image");
        }

        imageUtil.deleteImage(post.getImagePath());
        post.setImagePath(null);

        postRepo.save(post);
    }
}