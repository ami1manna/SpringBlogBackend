package com.example.blog_api.controller;

import com.example.blog_api.dto.ApiResponse;
import com.example.blog_api.dto.CommentCreateDTO;
import com.example.blog_api.dto.CommentDTO;
import com.example.blog_api.dto.CommentUpdateDTO;
import com.example.blog_api.service.impl.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CommentDTO>> create(@RequestBody CommentCreateDTO dto) {
        System.out.println( "create: " + dto);
        CommentDTO saved = commentService.create(dto);

        return new ResponseEntity<>(
                new ApiResponse<>(201, "Comment added successfully", saved),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> update(
            @PathVariable Long id,
            @RequestBody CommentUpdateDTO dto) {

        CommentDTO updated = commentService.update(id, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Comment updated successfully", updated)
        );
    }

    // delete comment by commentId - user
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

        commentService.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Comment deleted successfully", "OK")
        );
    }

    // get comment by CommentId
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> getById(@PathVariable Long id) {

        CommentDTO dto = commentService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Comment fetched successfully", dto)
        );
    }

    // get list of comments by postId
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getByPost(@PathVariable Long postId) {

        List<CommentDTO> list = commentService.getAllCommentsForPost(postId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Comments fetched successfully", list)
        );
    }


}
