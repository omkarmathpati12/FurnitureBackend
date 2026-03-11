package com.furniture.config;

import com.furniture.entity.Cart;
import com.furniture.entity.Product;
import com.furniture.entity.User;
import com.furniture.repository.CartRepository;
import com.furniture.repository.ProductRepository;
import com.furniture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private CartRepository cartRepository;

        @Override
        public void run(String... args) {
                // Create admin user if not exists
                if (!userRepository.existsByUsername("admin")) {
                        User admin = new User();
                        admin.setUsername("admin");
                        admin.setPassword("admin123"); // Simple plain text password for beginner project
                        admin.setEmail("admin@furniture.com");
                        admin.setFullName("Admin User");
                        admin.setRole(User.Role.ADMIN);
                        userRepository.save(admin);
                        System.out.println("✅ Admin user created: admin / admin123");
                }

                // Seed products if none exist
                if (productRepository.count() == 0) {
                        List<Product> products = List.of(
                                        buildProduct("Luxury Royal Sofa",
                                                        "Premium 3-seater sofa with velvet upholstery and solid wood frame. Perfect centerpiece for any living room.",
                                                        new BigDecimal("45999"), 15, Product.Category.SOFA,
                                                        "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=600",
                                                        "Velvet & Solid Wood", "220cm x 85cm x 90cm", 4.8),
                                        buildProduct("Minimalist L-Shape Sofa",
                                                        "Modern sectional sofa with clean lines in premium fabric. Ideal for spacious living areas.",
                                                        new BigDecimal("67999"), 8, Product.Category.SOFA,
                                                        "https://images.unsplash.com/photo-1567016432779-094069958ea5?w=600",
                                                        "Premium Fabric & Metal", "280cm x 160cm x 80cm", 4.6),
                                        buildProduct("King Size Platform Bed",
                                                        "Elegant king bed with upholstered headboard and storage drawers. Combines style with functionality.",
                                                        new BigDecimal("38999"), 12, Product.Category.BED,
                                                        "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600",
                                                        "Engineered Wood & Fabric", "200cm x 200cm x 120cm", 4.9),
                                        buildProduct("Scandinavian Dining Table",
                                                        "6-seater solid oak dining table with clean Scandinavian design. Built to last generations.",
                                                        new BigDecimal("29999"), 20, Product.Category.TABLE,
                                                        "https://images.unsplash.com/photo-1617098900591-3f90928e8c54?w=600",
                                                        "Solid Oak", "180cm x 90cm x 75cm", 4.7),
                                        buildProduct("Ergonomic Office Chair",
                                                        "Premium ergonomic chair with lumbar support, adjustable armrests, and breathable mesh back.",
                                                        new BigDecimal("18999"), 30, Product.Category.CHAIR,
                                                        "https://images.unsplash.com/photo-1592078615290-033ee584e267?w=600",
                                                        "Mesh & Aluminum", "65cm x 65cm x 120cm", 4.5),
                                        buildProduct("Vintage Wardrobe 4-Door",
                                                        "Classic 4-door wardrobe with full-length mirror panels and ample storage space.",
                                                        new BigDecimal("52999"), 7, Product.Category.WARDROBE,
                                                        "https://images.unsplash.com/photo-1595428773149-dbbde9a1b89d?w=600",
                                                        "Solid Wood & Mirror Glass", "200cm x 60cm x 220cm", 4.6),
                                        buildProduct("Floating Wall Shelf Set",
                                                        "Set of 3 floating shelves in rustic walnut finish. Perfect for books, plants and décor.",
                                                        new BigDecimal("4999"), 50, Product.Category.SHELF,
                                                        "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600",
                                                        "MDF & Metal Brackets", "80cm x 20cm each", 4.3),
                                        buildProduct("Executive Writing Desk",
                                                        "Spacious executive desk with cable management, drawer unit and premium walnut finish.",
                                                        new BigDecimal("24999"), 18, Product.Category.DESK,
                                                        "https://images.unsplash.com/photo-1593062096033-9a26b09da705?w=600",
                                                        "Solid Walnut Wood & Steel", "160cm x 80cm x 76cm", 4.8),
                                        buildProduct("Accent Armchair",
                                                        "Mid-century modern armchair in premium boucle fabric with solid walnut legs. Statement piece.",
                                                        new BigDecimal("14999"), 25, Product.Category.CHAIR,
                                                        "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600",
                                                        "Boucle Fabric & Walnut", "75cm x 80cm x 85cm", 4.7),
                                        buildProduct("Coffee Table with Storage",
                                                        "Modern coffee table with lower shelf and lift-top storage mechanism. Functionality meets style.",
                                                        new BigDecimal("12999"), 22, Product.Category.TABLE,
                                                        "https://images.unsplash.com/photo-1611269154421-4e27233ac5c7?w=600",
                                                        "Engineered Wood & Metal", "120cm x 60cm x 45cm", 4.4),
                                        buildProduct("Queen Bed with Headboard",
                                                        "Upholstered queen bed with tufted velvet headboard for a royal bedroom experience.",
                                                        new BigDecimal("28999"), 10, Product.Category.BED,
                                                        "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=600",
                                                        "Velvet & Plywood", "160cm x 200cm x 130cm", 4.6),
                                        buildProduct("Bookshelf 5-Tier",
                                                        "Industrial style 5-tier bookshelf combining metal frame with natural wood shelves.",
                                                        new BigDecimal("7999"), 35, Product.Category.SHELF,
                                                        "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=600",
                                                        "Metal & Pine Wood", "80cm x 30cm x 180cm", 4.5));
                        productRepository.saveAll(products);
                        System.out.println("✅ " + products.size() + " sample products seeded");
                }
        }

        private Product buildProduct(String name, String desc, BigDecimal price, int stock,
                        Product.Category category, String imageUrl,
                        String material, String dimensions, double rating) {
                Product p = new Product();
                p.setName(name);
                p.setDescription(desc);
                p.setPrice(price);
                p.setStockQuantity(stock);
                p.setCategory(category);
                p.setImageUrl(imageUrl);
                p.setMaterial(material);
                p.setDimensions(dimensions);
                p.setRating(rating);
                p.setReviewCount((int) (rating * 20));
                p.setIsActive(true);
                return p;
        }
}
