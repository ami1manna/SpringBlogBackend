package com.example.blog_api.service.impl;

import com.example.blog_api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(CategoryDTO categoryDTO);
    void delete(Long id);
    CategoryDTO getById(Long id);
    CategoryDTO getBySlug(String slug);
    List<CategoryDTO> getAll();

}
