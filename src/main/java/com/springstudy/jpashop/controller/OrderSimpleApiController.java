package com.springstudy.jpashop.controller;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.dto.OrderSimpleDto;
import com.springstudy.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import com.springstudy.jpashop.repository.OrderRepository;
import com.springstudy.jpashop.repository.OrderSearch;
import com.springstudy.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 * */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        OrderSearch orderSearch = new OrderSearch();
        List<Order> orders;
        if(orderSearch.getMemberName() == null && orderSearch.getOrderStatus() == null) {
            orders = orderService.findAll();
        } else {
            orders = orderService.findOrders(orderSearch);
        }
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return orders;
    }
    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleDto> ordersV2(){
        OrderSearch orderSearch = new OrderSearch();

        List<Order> orders;
        if(orderSearch.getMemberName() == null && orderSearch.getOrderStatus() == null) {
            orders = orderService.findAll();
        } else {
            orders = orderService.findOrders(orderSearch);
        }
        List<OrderSimpleDto> orderSimpleDtos = orders.stream()
                                                .map(o -> new OrderSimpleDto(o))
                                                .collect(Collectors.toList());
        return orderSimpleDtos;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleDto> ordersV3(){
        List<Order> orders = orderService.findAllWithMemberDelivery();
        List<OrderSimpleDto> orderSimpleDtos = orders.stream()
                .map(o -> new OrderSimpleDto(o))
                .collect(Collectors.toList());
        return orderSimpleDtos;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        return orderService.finOrderDtos();
    }
}
