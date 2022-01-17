package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.dto.CategoryDto;
import com.geekbrains.spring.web.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    public Category categoryFromDto(CategoryDto categoryDto){
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
    public CategoryDto dtoFromCategory(Category category){
        return new CategoryDto(category.getId(), category.getName());
    }
}
