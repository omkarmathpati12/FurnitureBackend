package com.furniture.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    private String phone;

    @NotNull(message = "Payment method is required")
    private String paymentMethod = "Cash on Delivery";
}
