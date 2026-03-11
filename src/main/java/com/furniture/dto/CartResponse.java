package com.furniture.dto;

import com.furniture.entity.Cart;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartResponse {
    private Long id;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
    private Integer totalItems;

    public static CartResponse from(Cart cart) {
        CartResponse res = new CartResponse();
        res.setId(cart.getId());
        List<CartItemResponse> items = cart.getCartItems().stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        res.setItems(items);
        res.setTotalAmount(items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        res.setTotalItems(items.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum());
        return res;
    }
}
