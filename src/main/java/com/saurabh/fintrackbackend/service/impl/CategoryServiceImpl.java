package com.saurabh.fintrackbackend.service.impl;

import com.saurabh.fintrackbackend.dto.CategoryDTO;
import com.saurabh.fintrackbackend.exception.DuplicateCreationException;
import com.saurabh.fintrackbackend.exception.ResourceNotFoundException;
import com.saurabh.fintrackbackend.model.Category;
import com.saurabh.fintrackbackend.repository.CategoryRepository;
import com.saurabh.fintrackbackend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new ResourceNotFoundException("No categories found");
        }
        return categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if(existingCategory != null){throw new DuplicateCreationException("Category with name '" + categoryDTO.getName() + "' already exists"); }
        Category category = categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Category duplicateCategory = categoryRepository.findByName(categoryDTO.getName());
        if(duplicateCategory != null){
            throw new DuplicateCreationException("Category with name '" + categoryDTO.getName() + "' already exists"); }
        existingCategory.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return "Category with id: '" + categoryId + "' deleted successfully";
    }
}
