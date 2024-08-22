package com.app.labdesoftware.services;

import com.app.labdesoftware.entities.Item;
import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.repositories.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void create() {
        Product product = new Product();
        Item item = new Item(product,2);

        when(itemRepository.save(item)).thenReturn(item);
        Item response = itemService.create(item);

        Assertions.assertEquals(item,response);
    }

}