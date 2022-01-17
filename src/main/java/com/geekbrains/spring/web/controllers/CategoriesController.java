package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;

    @GetMapping
    public Page<Category> getAllCategories(){
        return categoryService.findAll();
    }
}
