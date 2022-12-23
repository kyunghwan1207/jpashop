package com.springstudy.jpashop.controller;

import com.springstudy.jpashop.domain.Member;
import com.springstudy.jpashop.domain.Order;
import com.springstudy.jpashop.domain.item.Item;
import com.springstudy.jpashop.repository.OrderSearch;
import com.springstudy.jpashop.service.ItemService;
import com.springstudy.jpashop.service.MemberService;
import com.springstudy.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    @GetMapping
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders;
        log.info("orderSearch.getMemberName() = " + orderSearch.getMemberName());
        if(orderSearch.getMemberName().length() == 0 && orderSearch.getOrderStatus() == null){
            orders = orderService.findAll();
            log.info("null orders.toString() = " + orders.toString());
        } else {
            orders = orderService.findOrders(orderSearch);
            log.info("orders.toString() = " + orders.toString());
        }

        model.addAttribute("orders", orders);
        return "order/orderList";
    }
    @GetMapping("/new")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }
    @PostMapping("/new")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count); // 어떤 고객이 어떤 상품을 몇개 주문한다
        return "redirect:/home";
    }
    @PostMapping("/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
