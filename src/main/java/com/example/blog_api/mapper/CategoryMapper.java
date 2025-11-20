package com.example.blog_api.mapper;


import com.example.blog_api.dto.CategoryDTO;
import com.example.blog_api.entity.Category;

/**
 * Mapper for Category entity.
 */
public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getSlug()
        );
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        return category;
    }
}
