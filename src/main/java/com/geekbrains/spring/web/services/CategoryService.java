package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Page<Category> findAll(){
        Specification<Category> spec = Specification.where(null);
        return categoryRepository.findAll(spec, PageRequest.of( 0, 50));
    }
    public Optional<Category> findByName(String name){
        return categoryRepository.findByName(name);
    }
}
