package com.springstudy.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springstudy.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        // orderPrice를 item에서 안가져오고 별도로 args로 받는 이유는?
        orderItem.setOrderPrice(orderPrice); // 할인될 수도 있기 때문에!
        orderItem.setCount(count);

        item.removeStock(count); // 주문한 수량만큼 item에서 제거해줘야함
        return orderItem;
    }

    //===비즈니스 로직===//
    public void cancel() {
        this.getItem().addStock(count);
    }

    public int getTotalPrice() {
        return this.getOrderPrice() * this.getCount();
    }
}