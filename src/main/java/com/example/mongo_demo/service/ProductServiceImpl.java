package com.example.mongo_demo.service;

import com.example.mongo_demo.dto.CategoryCountResponse;
import com.example.mongo_demo.entity.Product;
import com.example.mongo_demo.repository.java.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Cacheable(value = "products", key = "'allProducts'")
    public List<Product> getAllProducts() {

        System.out.println("Fetching all products from mongodb");

        return productRepository.findAll();
    }


    @Override
    @Cacheable(value = "products",key="#id")
    public Product getProductById(String id) {
        System.out.println("Fetching product from mongodb");
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found"));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "products",key = "#id"),
                    @CacheEvict(value = "products",key = "'allProducts'")
            }
    )
    public Product updateProduct(String id, Product product) {

        System.out.println("Updating product in mongodb");

        Product existing=productRepository.findById(id).orElse(null);

        if(existing==null){
            return null;
        }

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setCategory(product.getCategory());
        existing.setStock(product.getStock());

        return productRepository.save(existing);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "products",key = "#id"),
                    @CacheEvict(value = "products",key = "'allProducts'")
            }
    )
    public void deleteProduct(String id) {
        System.out.println("Deleting product from mongodb");
        productRepository.deleteById(id);

    }

    @Override
    public List<Product> getProductByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findProductsByPriceRange(minPrice,maxPrice);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CategoryCountResponse> getCategoryWiseCount() {
        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.group("category")
                        .count().as("totalProducts"),

                Aggregation.project("totalProducts")
                        .and("_id").as("category")
        );

        AggregationResults<CategoryCountResponse> results =
                mongoTemplate.aggregate(
                        aggregation,
                        "products",
                        CategoryCountResponse.class);

        return results.getMappedResults();
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }
}
