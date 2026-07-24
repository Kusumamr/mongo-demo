package com.example.mongo_demo.controller;

import com.example.mongo_demo.dto.CategoryCountResponse;
import com.example.mongo_demo.entity.Product;
import com.example.mongo_demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @PutMapping("?{id}")
    public Product updateProduct(@PathVariable String id,@RequestBody Product product){
        return productService.updateProduct(id,product);
    }

    @DeleteMapping("?{id}")
    public void deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
    }

    @GetMapping("/price-range")
    public List<Product> getProductByPriceRange(@RequestParam Double minPrice,@RequestParam Double maxPrice){
        return productService.getProductByPriceRange(minPrice,maxPrice);
    }

    @GetMapping("/category-count")
    public List<CategoryCountResponse> geCategoryCount(){
        return productService.getCategoryWiseCount();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword){
        return productService.searchProducts(keyword);
    }
}
