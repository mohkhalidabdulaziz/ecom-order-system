package com.khalid.estore.service;

import com.khalid.estore.dto.req.CategoryRequestDTO;
import com.khalid.estore.dto.resp.CategoryResponseDTO;
import com.khalid.estore.entity.Category;
import com.khalid.estore.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponseDTO.class);
    }
}
