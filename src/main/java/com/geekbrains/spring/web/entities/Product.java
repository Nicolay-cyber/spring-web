package com.geekbrains.spring.web.entities;

import com.geekbrains.spring.web.converters.CategoryConverter;
import com.geekbrains.spring.web.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Integer price;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product(Long id, String title, Integer price, CategoryDto categoryDto) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = new CategoryConverter().categoryFromDto(categoryDto);
    }
    public CategoryDto getCategory(){
        return new CategoryConverter().dtoFromCategory(category);
    }

}
