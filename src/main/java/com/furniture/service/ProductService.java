package com.furniture.service;

import com.furniture.dto.ProductRequest;
import com.furniture.entity.Product;
import com.furniture.exception.BadRequestException;
import com.furniture.exception.ResourceNotFoundException;
import com.furniture.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findByIsActiveTrue();
    }

    public List<Product> getAllProductsForAdmin() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Product> getProductsByCategory(String category) {
        try {
            Product.Category cat = Product.Category.valueOf(category.toUpperCase());
            return productRepository.findByCategoryAndIsActiveTrue(cat);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category: " + category);
        }
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public List<Product> searchProductsByCategory(String keyword, String category) {
        try {
            Product.Category cat = Product.Category.valueOf(category.toUpperCase());
            return productRepository.searchProductsByCategory(keyword, cat);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category: " + category);
        }
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findTop8ByIsActiveTrueOrderByRatingDesc();
    }

    @Transactional
    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        mapRequestToProduct(request, product);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getProductById(id);
        mapRequestToProduct(request, product);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        product.setIsActive(false);
        productRepository.save(product);
    }

    @Transactional
    public void hardDeleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private void mapRequestToProduct(ProductRequest request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setMaterial(request.getMaterial());
        product.setDimensions(request.getDimensions());
    }
}
