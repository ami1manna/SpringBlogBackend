package com.example.blog_api.service;

import com.example.blog_api.dto.category.CategoryDTO;
import com.example.blog_api.entity.Category;
import com.example.blog_api.exception.BadRequestException;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.mapper.CategoryMapper;
import com.example.blog_api.respository.CategoryRepository;
import com.example.blog_api.service.impl.CategoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    // use Category repo
    private final CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        // find
        if(categoryRepo.existsByName(categoryDTO.getName())){
            throw new BadRequestException("Category name already exists: " + categoryDTO.getName());
        }

        Category category = CategoryMapper.toEntity(categoryDTO);
        category = categoryRepo.save(category);
        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO update(Long id , CategoryDTO categoryDTO) {
        // find
        Category category = categoryRepo.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
       category.setName(categoryDTO.getName());
       category.setSlug(categoryDTO.getSlug());
       category = categoryRepo.save(category);

       return CategoryMapper.toDTO(category);

    }

    @Override
    public void delete(Long id) {
        // find
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepo.delete(category);
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));


        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO getBySlug(String slug) {
        Category category = categoryRepo.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return CategoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepo.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
