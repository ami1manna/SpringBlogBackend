package com.example.blog_api.controller;

import com.example.blog_api.dto.api.ApiResponse;
import com.example.blog_api.dto.category.CategoryDTO;
import com.example.blog_api.service.impl.CategoryService;
import com.example.blog_api.utils.ResponseFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// swagger
@Tag(name = "Categories", description = "Category CRUD operations")
@SecurityRequirement(name = "bearerAuth")

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

        return ResponseFactory.created(saved , "Category updated successfully");

    }

    // update Category only admin
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> update(@PathVariable Long id,
                                                           @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updated = categoryService.update(id, categoryDTO);

        return ResponseFactory.ok(updated , "Category updated successfully");

    }

    // delete Category only admin
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseFactory.ok("OK" , "Category deleted successfully");

    }

    // get by id Category only admin
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getById(@PathVariable Long id) {

        CategoryDTO dto = categoryService.getById(id);
        return ResponseFactory.ok(dto , "Category fetched successfully");

    }

    // get by slug Category only admin
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getBySlug(@PathVariable String slug) {

        CategoryDTO dto = categoryService.getBySlug(slug);
        return ResponseFactory.ok(dto , "Category fetched successfully");

    }

    // get All Category only admin
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAll() {

        List<CategoryDTO> list = categoryService.getAll();
        return ResponseFactory.ok(list , "Categories fetched successfully");

    }
}
