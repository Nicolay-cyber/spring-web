package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.converters.CategoryConverter;
import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.ProductsRepository;
import com.geekbrains.spring.web.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partTitle, String categoryName, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if(categoryName != null){
            Category category = categoryService.findByName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена, имя:" + categoryName));
            spec = spec.and(ProductsSpecifications.categoryEqual(category));
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 50));
    }

    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public com.geekbrains.spring.web.soap.products.Product getById(Long id){
        return productsRepository.findById(id).map(functionEntityToSoap).get();
    }
    public static final Function<Product, com.geekbrains.spring.web.soap.products.Product> functionEntityToSoap = se -> {
        com.geekbrains.spring.web.soap.products.Product product = new com.geekbrains.spring.web.soap.products.Product();
        product.setId(se.getId());
        product.setTitle(se.getTitle());
        product.setCost(se.getPrice());
        product.setCategory(se.getCategory().getName());
        return product;
    };

    public com.geekbrains.spring.web.soap.products.Product getByTitle(String title){
        return productsRepository.findByTitle(title).map(functionEntityToSoap).get();
    }

    public List<com.geekbrains.spring.web.soap.products.Product> getAllProducts() {
        return productsRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }
}
