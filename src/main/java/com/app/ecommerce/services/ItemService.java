package com.app.ecommerce.services;

import com.app.ecommerce.entities.Item;
import com.app.ecommerce.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item create(Item item){
        return itemRepository.save(item);
    }
}
