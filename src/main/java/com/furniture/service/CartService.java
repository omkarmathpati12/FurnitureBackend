package com.furniture.service;

import com.furniture.dto.CartResponse;
import com.furniture.entity.Cart;
import com.furniture.entity.CartItem;
import com.furniture.entity.Product;
import com.furniture.entity.User;
import com.furniture.exception.BadRequestException;
import com.furniture.exception.ResourceNotFoundException;
import com.furniture.repository.CartItemRepository;
import com.furniture.repository.CartRepository;
import com.furniture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    public CartResponse getCart(String username) {
        Cart cart = getOrCreateCart(username);
        return CartResponse.from(cart);
    }

    @Transactional
    public CartResponse addToCart(String username, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(username);
        Product product = productService.getProductById(productId);

        if (product.getStockQuantity() < quantity) {
            throw new BadRequestException("Insufficient stock. Available: " + product.getStockQuantity());
        }
        if (!product.getIsActive()) {
            throw new BadRequestException("Product is no longer available");
        }

        // Check if product already in cart
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (cartItem != null) {
            int newQty = cartItem.getQuantity() + quantity;
            if (newQty > product.getStockQuantity()) {
                throw new BadRequestException("Cannot add more. Available stock: " + product.getStockQuantity());
            }
            cartItem.setQuantity(newQty);
            cartItemRepository.save(cartItem);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        return CartResponse.from(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Transactional
    public CartResponse updateCartItem(String username, Long itemId, Integer quantity) {
        Cart cart = getOrCreateCart(username);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Item does not belong to your cart");
        }

        if (quantity <= 0) {
            cart.getCartItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            if (quantity > item.getProduct().getStockQuantity()) {
                throw new BadRequestException("Insufficient stock. Available: " + item.getProduct().getStockQuantity());
            }
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return CartResponse.from(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Transactional
    public CartResponse removeFromCart(String username, Long itemId) {
        Cart cart = getOrCreateCart(username);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Item does not belong to your cart");
        }

        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);

        return CartResponse.from(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Transactional
    public void clearCart(String username) {
        Cart cart = getOrCreateCart(username);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }
}
