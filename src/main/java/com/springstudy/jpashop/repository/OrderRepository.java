package com.springstudy.jpashop.repository;

import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {
    private final EntityManager em;
    public void save(Order order){
        em.persist(order);
    }
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch){
        List<Order> orders;
        if (orderSearch.getMemberName() == null || orderSearch.getMemberName().length() == 0){
            log.info("orderSearch.getMemberName()  is NULL ");
            orders = em.createQuery("select o from Order o"
                                    + " where o.orderStatus = :status"
                            , Order.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .getResultList();
        } else if (orderSearch.getOrderStatus() == null){
            log.info("orderSearch.getStatus  is NULL ");
            orders = em.createQuery("select o from Order o join o.member m"
                                    + " where m.name like :name"
                            , Order.class)
                    .setParameter("name", orderSearch.getMemberName())
                    .getResultList();
        } else {
            log.info("orderSearch both not NULL ");
            orders = em.createQuery("select o from Order o join o.member m"
                                    + " where o.orderStatus = :status" + " and m.name like :name"
                            , Order.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .setParameter("name", orderSearch.getMemberName())
                    .getResultList();
        }
        return orders;
    }


    public List<Order> findAll() {
        return em.createQuery("select o from Order o join o.member m", Order.class)
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery(){
        return em.createQuery(
                "select o from Order o"
                + " join fetch o.member m"
                + " join fetch o.delivery d", Order.class
                ).getResultList();

    }

    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +
                " join fetch oi.item i", Order.class)
                .getResultList();
    }
}
