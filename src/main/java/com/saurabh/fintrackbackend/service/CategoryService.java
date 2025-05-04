package com.saurabh.fintrackbackend.service;

import com.saurabh.fintrackbackend.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long categoryId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);
}
