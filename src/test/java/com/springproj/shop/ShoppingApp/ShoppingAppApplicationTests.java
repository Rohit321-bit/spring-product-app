package com.springproj.shop.ShoppingApp;

import com.springproj.shop.ShoppingApp.dto.ProductDto;
import com.springproj.shop.ShoppingApp.entities.ProductEntity;
import com.springproj.shop.ShoppingApp.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@Slf4j
class ShoppingAppApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(ShoppingAppApplicationTests.class);
	@Autowired
	ProductRepository productRepository;
	@Test
	void contextLoads() {
	}
	@Test
	void test_product_create(){
		ProductEntity productEntity=ProductEntity.builder()
				.sku("nestle234")
				.title("Nestle Chocolate")
				.price(BigDecimal.valueOf(23.45))
				.quantity(4)
				.build();

		ProductEntity savedProductEntity = productRepository.save(productEntity);
		logger.info("Product Obj : "+savedProductEntity.toString());
	}
	@Test
	void getSingleFromRepository() {
		Optional<ProductEntity> product= productRepository
				.findByTitleAndPrice("Nest", BigDecimal.valueOf(23.45));
		product.ifPresent(System.out::println);
	}
}
