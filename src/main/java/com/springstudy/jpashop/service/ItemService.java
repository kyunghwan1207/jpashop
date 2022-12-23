package com.springstudy.jpashop.service;

import com.springstudy.jpashop.domain.item.Book;
import com.springstudy.jpashop.domain.item.Item;
import com.springstudy.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long id, String name, int price, String author, int stockQuantity, String isbn){
        Book book = (Book) itemRepository.findOne(id);
        book.setName(name);
        book.setPrice(price);
        book.setAuthor(author);
        book.setStockQuantity(stockQuantity);
        book.setIsbn(isbn);
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

}
