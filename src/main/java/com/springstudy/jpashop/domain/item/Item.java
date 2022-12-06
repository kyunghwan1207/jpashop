package com.springstudy.jpashop.domain.item;

import com.springstudy.jpashop.domain.Category;
import com.springstudy.jpashop.domain.OrderItem;
import com.springstudy.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
    //===비즈니스 로직===//
    // 재고 수(stockQuantity)를 늘리고, 줄이는 로직이기 때문에 해당 필드가 존재하는 엔티티(Item)에서
    // 로직 구현하는게 객체지향스럽고 좋다.
    /**
     * 재고(stock) 증가
     * */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    /**
     * 재고(stock) 감소
     * */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        } else {
            this.stockQuantity = restStock;
        }
    }


}