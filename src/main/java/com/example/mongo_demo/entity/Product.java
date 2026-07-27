package com.example.mongo_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    private String id;

    @Field("product_name")
    private String name;

    private String description;

    @Field("product-price")
    private Double price;

    private String category;

    private Integer stock;
}
