package com.springstudy.jpashop.repository;

import com.springstudy.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
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
            orders = em.createQuery("select o from Order o"
                                    + " where o.orderStatus = :status"
                            , Order.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .getResultList();
        } else if (orderSearch.getOrderStatus() == null){
            orders = em.createQuery("select o from Order o join o.member m"
                                    + " where m.name like :name"
                            , Order.class)
                    .setParameter("name", orderSearch.getMemberName())
                    .getResultList();
        } else {
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
}
