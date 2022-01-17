package com.geekbrains.spring.web.dto;

import com.geekbrains.spring.web.converters.CategoryConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Integer price;
    private CategoryDto category;
}
