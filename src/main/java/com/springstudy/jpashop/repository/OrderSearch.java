package com.springstudy.jpashop.repository;

import com.springstudy.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class OrderSearch {
    private String memberName;
//    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // ORDER, CANCEL
}
