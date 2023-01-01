package com.springstudy.jpashop.controller;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderItem;
import com.springstudy.jpashop.repository.OrderSearch;
import com.springstudy.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        OrderSearch orderSearch = new OrderSearch();

        List<Order> orders;
        if(orderSearch.getMemberName() == null && orderSearch.getOrderStatus() == null) {
            orders = orderService.findAll();
        } else {
            orders = orderService.findOrders(orderSearch);
        }
        // Tocuh 해서 영속성 컨텍스트로 올림
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }

        return orders;
    }
}
