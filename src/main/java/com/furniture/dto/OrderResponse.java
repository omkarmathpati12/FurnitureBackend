package com.furniture.dto;

import com.furniture.entity.Order;
import com.furniture.entity.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {
    private Long id;
    private String username;
    private String userFullName;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private Order.OrderStatus status;
    private String shippingAddress;
    private String phone;
    private String paymentMethod;
    private LocalDateTime createdAt;

    @Data
    public static class OrderItemDTO {
        private Long productId;
        private String productName;
        private String productImageUrl;
        private Integer quantity;
        private BigDecimal priceAtPurchase;
        private BigDecimal subtotal;

        public static OrderItemDTO from(OrderItem item) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setProductImageUrl(item.getProduct().getImageUrl());
            dto.setQuantity(item.getQuantity());
            dto.setPriceAtPurchase(item.getPriceAtPurchase());
            dto.setSubtotal(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));
            return dto;
        }
    }

    public static OrderResponse from(Order order) {
        OrderResponse res = new OrderResponse();
        res.setId(order.getId());
        res.setUsername(order.getUser().getUsername());
        res.setUserFullName(order.getUser().getFullName());
        res.setItems(order.getOrderItems().stream()
                .map(OrderItemDTO::from)
                .collect(Collectors.toList()));
        res.setTotalAmount(order.getTotalAmount());
        res.setStatus(order.getStatus());
        res.setShippingAddress(order.getShippingAddress());
        res.setPhone(order.getPhone());
        res.setPaymentMethod(order.getPaymentMethod());
        res.setCreatedAt(order.getCreatedAt());
        return res;
    }
}
