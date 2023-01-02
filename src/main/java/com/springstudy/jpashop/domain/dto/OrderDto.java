package com.springstudy.jpashop.domain.dto;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderItem;
import com.springstudy.jpashop.domain.OrderStatus;
import com.springstudy.jpashop.domain.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order){
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getOrderStatus();
        this.address = order.getDelivery().getAddress();
        order.getOrderItems().stream().forEach(oi -> oi.getItem().getName());
        this.orderItems = order.getOrderItems().stream()
                .map(oi -> new OrderItemDto(oi))
                .collect(Collectors.toList());
    }
}
