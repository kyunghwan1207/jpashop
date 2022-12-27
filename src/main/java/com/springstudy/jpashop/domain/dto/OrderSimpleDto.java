package com.springstudy.jpashop.domain.dto;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderStatus;
import com.springstudy.jpashop.domain.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderSimpleDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleDto(Order order){
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getOrderStatus();
        this.address = order.getDelivery().getAddress();
    }
}
