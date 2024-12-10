package com.khalid.estore.controller;


import com.khalid.estore.entity.Category;
import com.khalid.estore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public Category add(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
}
