package com.app.ecommerce.IntegrationTests;

import com.app.ecommerce.ContainerOfTests;
import com.app.ecommerce.controllers.order.ItemRequest;
import com.app.ecommerce.controllers.order.OrderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest extends ContainerOfTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should get a order made by the authenticated user (Admin)")
    @WithUserDetails("Admin")
    void get_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/order/{id}",4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a order made by the authenticated user (User)")
    @WithUserDetails("User")
    void get_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/order/{id}",5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get all the orders (Admin)")
    @WithUserDetails("Admin")
    void listAll_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/order/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get all the orders (User)")
    @WithUserDetails("User")
    void listAll_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/order/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should create a new order (Admin)")
    @WithUserDetails("Admin")
    void create_Admin() throws Exception {
        ItemRequest itemRequest = new ItemRequest(1,2);
        ItemRequest itemRequest2 = new ItemRequest(2,2);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest,itemRequest2));
        String jsonRequest = mapper.writeValueAsString(orderRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should create a new order (User)")
    @WithUserDetails("User")
    void create_User() throws Exception {
        ItemRequest itemRequest = new ItemRequest(1,2);
        ItemRequest itemRequest2 = new ItemRequest(2,2);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest,itemRequest2));
        String jsonRequest = mapper.writeValueAsString(orderRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}