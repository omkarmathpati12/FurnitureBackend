package com.furniture.dto;

import com.furniture.entity.CartItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String productImageUrl;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal subtotal;

    public static CartItemResponse from(CartItem item) {
        CartItemResponse res = new CartItemResponse();
        res.setId(item.getId());
        res.setProductId(item.getProduct().getId());
        res.setProductName(item.getProduct().getName());
        res.setProductImageUrl(item.getProduct().getImageUrl());
        res.setProductPrice(item.getProduct().getPrice());
        res.setQuantity(item.getQuantity());
        res.setSubtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return res;
    }
}
