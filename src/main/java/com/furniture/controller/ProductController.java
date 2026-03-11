package com.furniture.controller;

import com.furniture.entity.Product;
import com.furniture.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController handles all product-related public API requests.
 * This includes browsing, searching, and viewing product details.
 * 
 * @RestController: Marks this class as a Web Controller where every method
 *                  returns a domain object instead of a view.
 *                  @RequestMapping("/products"): Base URL path for all
 *                  endpoints in this controller.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Retrieves all products with optional filtering by keyword or category.
     * 
     * @param keyword:  Optional search term to filter products by name or
     *                  description.
     * @param category: Optional category filter (e.g., SOFA, CHAIR).
     * @return List of products matching the criteria.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category) {

        // Logic to decide which service method to call based on provided parameters
        if (keyword != null && !keyword.isBlank() && category != null && !category.isBlank()) {
            return ResponseEntity.ok(productService.searchProductsByCategory(keyword, category));
        } else if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(productService.searchProducts(keyword));
        } else if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(productService.getProductsByCategory(category));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Retrieves a selection of featured products for the home page.
     */
    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        return ResponseEntity.ok(productService.getFeaturedProducts());
    }

    /**
     * Retrieves a single product by its unique database ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
