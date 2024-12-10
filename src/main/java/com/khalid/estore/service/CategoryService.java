package com.khalid.estore.service;

import com.khalid.estore.dto.req.CategoryRequestDTO;
import com.khalid.estore.dto.resp.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO);
}
