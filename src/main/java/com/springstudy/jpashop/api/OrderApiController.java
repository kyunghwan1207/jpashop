package com.springstudy.jpashop.api;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderItem;
import com.springstudy.jpashop.domain.dto.OrderDto;
import com.springstudy.jpashop.repository.OrderSearch;
import com.springstudy.jpashop.repository.order.query.OrderFlatDto;
import com.springstudy.jpashop.repository.order.query.OrderItemQueryDto;
import com.springstudy.jpashop.repository.order.query.OrderQueryDto;
import com.springstudy.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderService.findAll();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderService.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
            ){
        List<Order> orders = orderService.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderService.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderService.findAllByDto_optimiation();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderFlatDto> ordersV6(){
        List<OrderFlatDto> flats = orderService.findAllByDto_flat();
        flats.stream()
                .collect(Collectors.groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                Collectors.mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())))
                .entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(Collectors.toList());

        return flats;
    }
}
