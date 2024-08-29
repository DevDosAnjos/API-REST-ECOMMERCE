package com.app.labdesoftware.IntegrationTests;

import com.app.labdesoftware.ContainerOfTests;
import com.app.labdesoftware.controllers.purchase.PurchaseRequest;
import com.app.labdesoftware.enumerations.PaymentMethod;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PurchaseControllerTest extends ContainerOfTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should get a purchase (Admin)")
    @WithUserDetails("Admin")
    void get_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/purchase/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a purchase (User)")
    @WithUserDetails("User")
    void get_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/purchase/{id}",5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all purchases (Admin)")
    @WithUserDetails("Admin")
    void listAll_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/purchase/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all purchases (User)")
    @WithUserDetails("User")
    void listAll_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/purchase/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should create a new purchase (Admin)")
    @WithUserDetails("Admin")
    void create_Admin() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, PaymentMethod.CREDIT_CARD,"IntegrationTest");
        String jsonRequest = mapper.writeValueAsString(purchaseRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should create a new purchase (User)")
    @WithUserDetails("User")
    void create_User() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(2, PaymentMethod.PIX,"IntegrationTest");
        String jsonRequest = mapper.writeValueAsString(purchaseRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}