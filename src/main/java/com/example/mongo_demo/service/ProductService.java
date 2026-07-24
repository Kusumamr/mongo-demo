package com.example.mongo_demo.service;

import com.example.mongo_demo.dto.CategoryCountResponse;
import com.example.mongo_demo.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(String id);

    Product updateProduct(String id,Product product);

    void deleteProduct(String id);

    List<Product> getProductByPriceRange(Double minPrice,Double maxPrice);

    List<CategoryCountResponse> getCategoryWiseCount();

    List<Product> searchProducts(String keyword);
}
