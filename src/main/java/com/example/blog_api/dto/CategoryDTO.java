package com.example.blog_api.dto;
/**
 * CategoryDTO
 * Provides basic info for category responses.
 */
public class CategoryDTO {

    private Long id;
    private String name;
    private String slug;

    public CategoryDTO(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
