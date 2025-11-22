package com.example.blog_api.controller;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.api.PaginatedResponse;
import com.example.blog_api.dto.comment.CommentCreateDTO;
import com.example.blog_api.dto.comment.CommentDTO;
import com.example.blog_api.dto.comment.CommentUpdateDTO;
import com.example.blog_api.service.impl.CommentService;
import com.example.blog_api.utils.ResponseFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//swagger
@Tag(name = "Comments", description = "Comment CRUD operations")
@SecurityRequirement(name = "bearerAuth")

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CommentDTO>> create(@RequestBody CommentCreateDTO dto) {

        CommentDTO saved = commentService.create(dto);
        return ResponseFactory.created(saved , "Comment added successfully");

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> update(
            @PathVariable Long id,
            @RequestBody CommentUpdateDTO dto) {

        CommentDTO updated = commentService.update(id, dto);
        return ResponseFactory.ok(updated , "Comment updated successfully");

    }

    // delete comment by commentId - user
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

        commentService.delete(id);
        return ResponseFactory.ok("OK" , "Comment deleted successfully");

    }

    // get comment by CommentId
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> getById(@PathVariable Long id) {

        CommentDTO dto = commentService.getById(id);
        return ResponseFactory.ok(dto , "Comment fetched successfully");

    }

    // get list of comments by postId
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<PaginatedResponse<CommentDTO>>> getByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CommentDTO> dtoPage = commentService.getCommentsForPost(postId, page, size);
        return ResponseFactory.paginated(dtoPage, "Comments fetched successfully");
    }


    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<PaginatedResponse<CommentDTO>>> getMyComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CommentDTO> dtoPage = commentService.getMyComments(page, size);
        return ResponseFactory.paginated(dtoPage, "My comments fetched successfully");
    }




}
