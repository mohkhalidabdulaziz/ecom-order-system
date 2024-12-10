package com.khalid.estore.controller;


import com.khalid.estore.dto.req.CategoryRequestDTO;
import com.khalid.estore.dto.resp.CategoryResponseDTO;
import com.khalid.estore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO savedCategory = categoryService.addCategory(categoryRequestDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }
}