package com.springproj.shop.ShoppingApp.controllers;

import com.springproj.shop.ShoppingApp.advices.ApiResponse;
import com.springproj.shop.ShoppingApp.dto.ProductDto;
import com.springproj.shop.ShoppingApp.entities.ProductEntity;
import com.springproj.shop.ShoppingApp.exceptions.ResourceNotFoundException;
import com.springproj.shop.ShoppingApp.services.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService,ModelMapper modelMapper){
        this.productService=productService;
    }
    @PostMapping(path="/createProduct")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody @Valid ProductDto productDto){
        ProductDto prod=productService.createProd(productDto);
        ApiResponse<ProductDto> apiResponse=new ApiResponse<>(prod);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping(path="/getProductOnTitleAndPrice")
    public ResponseEntity<ProductEntity> getProductOnTitleAnPrice(@RequestParam String title,
                                                                  @RequestParam Double price){
        Optional<ProductEntity> productDto=productService.getProductOnTitleAnPrice(title, BigDecimal.valueOf(price));
        return productDto.map(ResponseEntity::ok)
                .orElseThrow(()->new ResourceNotFoundException("Can not Find Record!"));
    }
    /**
     * Retrieves a list of  Five Products per page.
     *
     * @param pageNum The page number to retrieve (starting from 0).
     * @param sortBy Retrieve products per page ascending order by their Ids.
     * @return An array of products on the specified page.
     */
    @GetMapping(path="/fetchFiveProducts")
    public ResponseEntity<List<ProductEntity>> getFiveProducts(@RequestParam(defaultValue = "") String title,
                                               @RequestParam(defaultValue = "id") String sortBy,
                                               @RequestParam(defaultValue = "0") Integer pageNum){
        int PAGE_SIZE = 5;
        return ResponseEntity.ok(productService.getFiveProducts(title,sortBy,pageNum, PAGE_SIZE));
    }

    /**
     * Retrieve All The Products in a List
     * @return An array of products
     */
    @GetMapping("/getAllProducts")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProductList(){
        List<ProductDto> productDtoList=productService.getAllProducts();
        ApiResponse<List<ProductDto>> apiResponse=new ApiResponse<>(productDtoList);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping(path="/getByTitle")
    public ResponseEntity<List<ProductDto>> getProdByTitle(@RequestParam(defaultValue = "") String title){
        return ResponseEntity.ok(productService.getProductByTitle(title));
    }
    @GetMapping(path="getProductById/{prodId}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable(name="prodId") Long id){
        ApiResponse<ProductDto> apiResponse=new ApiResponse<>(productService.getProductById(id));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @DeleteMapping(path="/deleteProduct/{prodId}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long prodId)
    {
        boolean isDeleted=productService.deleteProduct(prodId);
        if(isDeleted){
            ApiResponse<String> apiResponse=new ApiResponse<>("SuccessFully Deleted Product!");
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>("Could Not Delete the Product!"),HttpStatus.NOT_FOUND);
    }

    /**
     * This method is using to update a portion of Product Object
     * @param prodId This is Product Id
     * @param updates This is request body which need to be updated
     * @return Product Object
     */
    @PatchMapping(path="/updateProduct/{prodId}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable Long prodId, @RequestBody Map<String,Object> updates){
    ProductDto productDto=productService.updatePartialProduct(prodId,updates);
    if (productDto == null) return new ResponseEntity<>(new ApiResponse<>("Could Not Found Product with the Id!"),HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(new ApiResponse<>(productDto),HttpStatus.OK);
    }
}
