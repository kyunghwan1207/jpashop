package com.springstudy.jpashop.controller;

import com.springstudy.jpashop.domain.item.Book;
import com.springstudy.jpashop.domain.item.Item;
import com.springstudy.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    @GetMapping
    public String itemList(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }
    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm.html";
    }
    @PostMapping("/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }
    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long id, Model model){
        Book item = (Book) itemService.findOne(id);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setAuthor(item.getAuthor());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemform";
    }
    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long id, @ModelAttribute("form") BookForm form){
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        itemService.updateItem(id, form.getName(), form.getPrice(), form.getAuthor(), form.getStockQuantity(), form.getIsbn());


        return "redirect:/items";
    }
}
