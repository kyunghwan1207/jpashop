package com.springstudy.jpashop.domain.dto;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {
    private String itemName; // 상품 명
    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    public OrderItemDto(OrderItem orderItem){
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }

}
