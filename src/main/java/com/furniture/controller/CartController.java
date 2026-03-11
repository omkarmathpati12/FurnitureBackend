package com.furniture.controller;

import com.furniture.dto.CartResponse;
import com.furniture.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam String username) {
        return ResponseEntity.ok(cartService.getCart(username));
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @RequestParam String username,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        return ResponseEntity.ok(cartService.addToCart(username, productId, quantity));
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @RequestParam String username,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(username, itemId, quantity));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<CartResponse> removeFromCart(
            @RequestParam String username,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeFromCart(username, itemId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearCart(@RequestParam String username) {
        cartService.clearCart(username);
        return ResponseEntity.ok(Map.of("message", "Cart cleared"));
    }
}
