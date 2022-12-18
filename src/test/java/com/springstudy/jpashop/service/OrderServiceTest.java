package com.springstudy.jpashop.service;

import com.springstudy.jpashop.domain.Member;
import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.OrderStatus;
import com.springstudy.jpashop.domain.embedded.Address;
import com.springstudy.jpashop.domain.item.Book;
import com.springstudy.jpashop.exception.NotEnoughStockException;
import com.springstudy.jpashop.repository.OrderRepository;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);
        em.persist(book);
        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, findOrder.getOrderStatus(), "상품 주문시 상태는 ORDER여야 한다");
        assertEquals(1, findOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * orderCount, findOrder.getTotalPrice(), "주문 가격은 가격 x 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    @DisplayName("주문 취소")
    public void 주문취소() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("시골 JPA2", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // when
        orderService.cancelOrder(orderId);
        // then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, findOrder.getOrderStatus(), "주문 취소시 상태틑 CANCEL이다");
        assertEquals(10, book.getStockQuantity(), "주문 취소시 취소된 상품은 그만큼 재고가 증가해야한다.");

    }
    @Test
    @DisplayName("상품주문 재고 수량 초과")
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("시골JPA", 10000, 10);
        int orderCount = 11;
        // when
        // then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    private Book createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }

}