package com.springstudy.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springstudy.jpashop.domain.embedded.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    // Order가 FK를 가지고 있기에 Order는 연관관계 주인이다
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
    @Embedded
    private Address address;
}
