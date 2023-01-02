package com.springstudy.jpashop.service;

import com.springstudy.jpashop.domain.Delivery;
import com.springstudy.jpashop.domain.Member;
import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderItem;
import com.springstudy.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import com.springstudy.jpashop.domain.item.Item;
import com.springstudy.jpashop.repository.ItemRepository;
import com.springstudy.jpashop.repository.MemberRepository;
import com.springstudy.jpashop.repository.OrderRepository;
import com.springstudy.jpashop.repository.OrderSearch;
import com.springstudy.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item  = itemRepository.findOne(itemId);

        // 배송정보 조회
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }
    /**
     * 주문 취소
     * */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order findOrder = orderRepository.findOne(orderId);
        // 주문 취소
        findOrder.cancel();
    }
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public List<Order> findAllWithMemberDelivery() {
        return orderRepository.findAllWithMemberDelivery();
    }

    public List<OrderSimpleQueryDto> finOrderDtos() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    public List<Order> findAllWithItem() {
        return orderRepository.findAllWithItem();
    }
}
