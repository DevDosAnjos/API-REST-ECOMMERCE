package com.app.ecommerce.UnitTests;

import com.app.ecommerce.entities.Item;
import com.app.ecommerce.entities.Order;
import com.app.ecommerce.entities.Product;
import com.app.ecommerce.repositories.ItemRepository;
import com.app.ecommerce.services.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    @DisplayName("Should create a new item")
    void create() {
        Product product = new Product();
        Order order = new Order();
        Item item = new Item(product,2);

        when(itemRepository.save(item)).thenReturn(item);
        Item response = itemService.create(item);

        Assertions.assertEquals(item,response);
    }

}