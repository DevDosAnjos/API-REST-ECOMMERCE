package com.app.labdesoftware.services;

import com.app.labdesoftware.entities.Item;
import com.app.labdesoftware.repositories.ItemRepository;
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
