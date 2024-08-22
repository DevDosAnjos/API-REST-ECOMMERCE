package com.app.labdesoftware.controllers;

import com.app.labdesoftware.ContainerOfTests;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest extends ContainerOfTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp(){
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db.migration/test")
                .load();
        flyway.migrate();
    }

    @Test
    @DisplayName("Should get a order made by the authenticated user")
    @WithMockUser(roles = {"ADMIN"})
    void get() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/order/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get all the orders")
    @WithMockUser(roles = {"ADMIN"})
    void listAll() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/order/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should create a new order")
    @WithMockUser(roles = {"ADMIN"})
    void create() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/order"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}