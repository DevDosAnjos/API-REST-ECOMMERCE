package com.app.labdesoftware.controllers;

import com.app.labdesoftware.ContainerOfTests;
import com.app.labdesoftware.controllers.purchase.PurchaseRequest;
import com.app.labdesoftware.enumerations.PaymentMethod;
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
class PurchaseControllerTest extends ContainerOfTests {

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
    @DisplayName("Should get a purchase")
    @WithMockUser(roles = {"ADMIN"})
    void get() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/purchase/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all purchases")
    @WithMockUser(roles = {"ADMIN"})
    void listAll() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/purchase/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should create a new purchase")
    @WithMockUser(roles = {"ADMIN"})
    void create() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, PaymentMethod.CREDIT_CARD,"IntegrationTest");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}