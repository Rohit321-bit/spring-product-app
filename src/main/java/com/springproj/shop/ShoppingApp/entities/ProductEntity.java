package com.springproj.shop.ShoppingApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="product_table",
    uniqueConstraints = {
        @UniqueConstraint(name="title_price_unique",columnNames = {"title_x","price"})
    },
    indexes={
        @Index(name="sku_index",columnList = "sku")
    })
public class ProductEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name="title_x")
    private String title;
    private String sku;
    @NotNull(message="Please select at least one Quantity")
    private Integer quantity;
    @DecimalMin(value="1.00000")
    private BigDecimal price;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
