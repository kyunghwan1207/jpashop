package com.springstudy.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new java.util.ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태 [ORDER, CANCEL]

    //=== 연관관계 메서드 ===//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //===생성 메서드===//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        // Member와 Delivery 정보가 필요하고, OrderItem이 여러개 일 수 있기 떄문에 ...으로 정의
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderitem: orderItems) {
            order.addOrderItem(orderitem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    //===비즈니스 로직====//
    /**
     * 주문 취소
     * */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setOrderStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: this.orderItems) {
            orderItem.cancel();
        }
    }
    //===조회 로직===//
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem: orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

//        int totalPrice2 = orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();

//        int totalPrice3 = orderItems.stream()
//                .map((orderItem) -> orderItem.getTotalPrice())
//                .reduce(0, (a, b) -> a + b);

        return totalPrice;
    }

}
