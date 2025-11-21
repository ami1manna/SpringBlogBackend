package com.example.blog_api.controller;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.category.CategoryDTO;
import com.example.blog_api.service.impl.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // create Category only admin
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> create(@RequestBody CategoryDTO categoryDTO) {

        CategoryDTO saved = categoryService.create(categoryDTO);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Category updated successfully",
                        saved)
        );
    }

    // update Category only admin
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> update(@PathVariable Long id,
                                                           @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updated = categoryService.update(id, categoryDTO);

        return ResponseEntity.ok(
                new ApiResponse<>(200 , "Category updated successfully" ,updated)
        );
    }

    // delete Category only admin
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Category deleted successfully", "OK")
        );
    }

    // get by id Category only admin
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getById(@PathVariable Long id) {

        CategoryDTO dto = categoryService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Category fetched successfully", dto)
        );
    }

    // get by slug Category only admin
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getBySlug(@PathVariable String slug) {

        CategoryDTO dto = categoryService.getBySlug(slug);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Category fetched successfully", dto)
        );
    }

    // get All Category only admin
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAll() {

        List<CategoryDTO> list = categoryService.getAll();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Categories fetched successfully", list)
        );
    }
}
