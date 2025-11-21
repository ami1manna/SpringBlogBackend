package com.example.blog_api.controller;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.post.PostCreateDTO;
import com.example.blog_api.dto.post.PostDTO;
import com.example.blog_api.dto.post.PostUpdateDTO;
import com.example.blog_api.service.impl.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // add new post - user
    @PostMapping
    public ResponseEntity<ApiResponse<PostDTO>> create(@RequestBody PostCreateDTO dto) {

        PostDTO saved = postService.create(dto);

        return new ResponseEntity<>(
                new ApiResponse<>(201, "Post created successfully", saved),
                HttpStatus.CREATED
        );
    }

    // update post by id - author
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> update(
            @PathVariable Long id,
            @RequestBody PostUpdateDTO dto) {

        PostDTO updated = postService.update(id, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Post updated successfully", updated)
        );
    }

    // delete post by id - author
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

        postService.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Post deleted successfully", "OK")
        );
    }

    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getById(@PathVariable Long id) {

        PostDTO dto = postService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Post fetched successfully", dto)
        );
    }

    // get post by slug
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<PostDTO>> getBySlug(@PathVariable String slug) {

        PostDTO dto = postService.getBySlug(slug);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Post fetched successfully", dto)
        );
    }

    // get list of post by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getByCategory(@PathVariable Long categoryId) {

        List<PostDTO> list = postService.getAllByCategory(categoryId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Posts fetched successfully", list)
        );
    }

    // get list of  post by author
    @GetMapping("/author/{authorId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getByAuthor(@PathVariable Long authorId) {

        List<PostDTO> list = postService.getAllByAuthor(authorId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Posts fetched successfully", list)
        );
    }

    // get list of post by title
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PostDTO>>> searchByTitle(@RequestParam String keyword) {

        List<PostDTO> list = postService.searchByTitle(keyword);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Search results", list)
        );
    }

    // get all post
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAll() {

        List<PostDTO> list = postService.getAll();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Posts fetched successfully", list)
        );
    }


}
