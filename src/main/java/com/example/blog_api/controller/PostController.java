package com.example.blog_api.controller;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.api.PaginatedResponse;
import com.example.blog_api.dto.post.PostCreateDTO;
import com.example.blog_api.dto.post.PostDTO;
import com.example.blog_api.dto.post.PostUpdateDTO;
import com.example.blog_api.service.impl.PostService;
import com.example.blog_api.utils.ResponseFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// for swagger
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Posts", description = "Post CRUD operations")

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<PostDTO>> create(@RequestBody PostCreateDTO dto) {

        PostDTO saved = postService.create(dto);

        return ResponseFactory.created(saved , "Post created successfully");
    }

    // update post by id - author
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> update(
            @PathVariable Long id,
            @RequestBody PostUpdateDTO dto) {

        PostDTO updated = postService.update(id, dto);

        return ResponseFactory.ok(updated , "Post updated successfully");

    }

    // delete post by id - author
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

        postService.delete(id);

        return ResponseFactory.ok("OK" , "Post deleted successfully");

    }

    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getById(@PathVariable Long id) {

        PostDTO dto = postService.getById(id);

        return ResponseFactory.ok(dto , "Post fetched successfully");

    }

    // get post by slug
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<PostDTO>> getBySlug(@PathVariable String slug) {

        PostDTO dto = postService.getBySlug(slug);
        return ResponseFactory.ok(dto , "Post fetched successfully");

    }

    // get list of post by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<PaginatedResponse<PostDTO>>> getByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostDTO> dtoPage = postService.getAllByCategory(categoryId, page, size);
        return ResponseFactory.paginated(dtoPage, "Posts by category fetched");
    }

    // get list of  post by author
    @GetMapping("/author/{authorId}")
    public ResponseEntity<ApiResponse<PaginatedResponse<PostDTO>>> getByAuthor(@PathVariable Long authorId ,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {

        Page<PostDTO> dtoPage = postService.getAllByAuthor(authorId , page , size);

        return ResponseFactory.paginated( dtoPage , "Post by Author fetched");

    }

    // get list of post by title
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PaginatedResponse<PostDTO>>> searchByTitle(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostDTO> dtoPage = postService.searchByTitle(keyword, page, size);
        return ResponseFactory.paginated(dtoPage, "Search results");
    }


    // get all post
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<PostDTO>>>getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostDTO> dtoPage = postService.getAll(page, size);
        return ResponseFactory.paginated(dtoPage, "Posts fetched successfully");
    }


    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<PaginatedResponse<PostDTO>>> getMyPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostDTO> dtoPage = postService.getMyPosts(page, size);
        return ResponseFactory.paginated(dtoPage, "My posts fetched successfully");
    }

    //Images
    @PostMapping("{postId}/image")
    public ResponseEntity<ApiResponse<PostDTO>> uploadImage(
            @PathVariable Long postId,
            @RequestParam("image") MultipartFile image
            ){

        PostDTO dto = postService.uploadImage(postId, image);
        return ResponseFactory.ok(dto , "Post image uploaded successfully");

    }

    @GetMapping("/{postId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long postId) {

        byte[] image = postService.getImageBytes(postId);

        return  ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }

    @DeleteMapping("/{postId}/image")
    public ResponseEntity<ApiResponse<String>> deleteImage(@PathVariable Long postId) {

        postService.deleteImage(postId);

        return ResponseEntity.ok(new ApiResponse<>(200, "Image deleted", "OK"));
    }



}
