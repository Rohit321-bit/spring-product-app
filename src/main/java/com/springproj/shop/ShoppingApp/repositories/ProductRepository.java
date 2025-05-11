package com.springproj.shop.ShoppingApp.repositories;

import com.springproj.shop.ShoppingApp.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("select e from ProductEntity e where e.title=:title and e.price=:price")
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);
    List<ProductEntity> findByTitleLike(String title);
    List<ProductEntity> findByTitleContainingIgnoreCase(String title, Pageable page);
}
