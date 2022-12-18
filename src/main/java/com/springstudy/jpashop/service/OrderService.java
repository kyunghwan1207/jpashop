package com.springstudy.jpashop.service;

import com.springstudy.jpashop.domain.Delivery;
import com.springstudy.jpashop.domain.Member;
import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderItem;
import com.springstudy.jpashop.domain.item.Item;
import com.springstudy.jpashop.repository.ItemRepository;
import com.springstudy.jpashop.repository.MemberRepository;
import com.springstudy.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
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
}
