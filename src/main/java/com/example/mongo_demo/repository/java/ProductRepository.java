package com.example.mongo_demo.repository.java;

import com.example.mongo_demo.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {

    @Query("{ 'price' : { $gte: ?0, $lte: ?1 } }")
    List<Product> findProductsByPriceRange(double min, double max);

    @Query("{ $text : { $search : ?0 } }")
    List<Product> searchProducts(String keyword);
}
