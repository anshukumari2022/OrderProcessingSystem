package com.test.jms.OrderProcessingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {
    private String orderId;
    private String customerName;
    private String email;
    private String shippingAddress;
    private List<OrderItem> orderItems;
    private double totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus status;

}
