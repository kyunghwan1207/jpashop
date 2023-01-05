package com.springstudy.jpashop.service.query;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.dto.OrderDto;
import com.springstudy.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {
    private final OrderService orderService;
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderService.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }
}

