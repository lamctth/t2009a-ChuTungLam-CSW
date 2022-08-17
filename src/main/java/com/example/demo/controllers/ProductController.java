package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    // Add New Products
    @PostMapping
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    // Get 1 Product ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update 1 Product
    @PutMapping("/{id}")
    public ResponseEntity<Product> sellProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> categoryOptional = productService.findById(id);
        return categoryOptional.map(product1 -> {
            product.setId(product1.getId());
            product.setQuantity(product1.getQuantity() - 1);
            return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
