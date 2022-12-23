package com.springstudy.jpashop.domain;

import com.springstudy.jpashop.domain.embedded.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @NotEmpty
    private String name;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
