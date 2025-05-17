package com.springproj.shop.ShoppingApp.services;

import com.springproj.shop.ShoppingApp.dto.ProductDto;
import com.springproj.shop.ShoppingApp.entities.ProductEntity;
import com.springproj.shop.ShoppingApp.exceptions.ResourceNotFoundException;
import com.springproj.shop.ShoppingApp.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    public ProductService(ProductRepository productRepository,ModelMapper modelMapper){
        this.productRepository=productRepository;
        this.modelMapper=modelMapper;
    }

    public ProductDto createProd(ProductDto productDto) {
        ProductEntity productEntity=modelMapper.map(productDto, ProductEntity.class);
        ProductEntity saveProd=productRepository.save(productEntity);
        return modelMapper.map(saveProd, ProductDto.class);
    }

    public Optional<ProductEntity> getProductOnTitleAnPrice(String title, BigDecimal price) {

        return productRepository.findByTitleAndPrice(title, (price));
    }

    public List<ProductEntity> getFiveProducts(String title, String sortBy, Integer pageNum, int pageSize) {
        return productRepository.findByTitleContainingIgnoreCase(title,
                PageRequest.of(pageNum,pageSize, Sort.by(sortBy))
        );
    }
    public List<ProductDto> getProductByTitle(String title){
        List<ProductEntity> prod=productRepository.findByTitleLike(title);
        return prod.stream().map(e->modelMapper.map(e, ProductDto.class)).toList();
    }
    public ProductDto updatePartialProduct(Long id, Map<String,Object> updates){
        ifExist(id);
        ProductEntity productEntity=productRepository.findById(id).get();
        updates.forEach((field,value)->{
            Field fieldToBeUpdated= ReflectionUtils.findRequiredField(ProductEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,productEntity,value);
        });
        return modelMapper.map(productRepository.save(productEntity),ProductDto.class);
    }
    public boolean deleteProduct(Long id){
        ifExist(id);
        productRepository.deleteById(id);
        return true;
    }
    public ProductDto getProductById(Long id){
        ifExist(id);
        return modelMapper.map(productRepository.findById(id),ProductDto.class);
    }
    public void ifExist(Long prodId){
        boolean exists=productRepository.existsById(prodId);
        if(!exists) throw new ResourceNotFoundException("Product not found with id: "+prodId);
    }

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> productEntityList=productRepository.findAll();
        return productEntityList.stream().
                map(e->modelMapper.map(e,ProductDto.class)).toList();
     }
}
