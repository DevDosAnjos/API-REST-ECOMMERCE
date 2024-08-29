package com.app.labdesoftware.IntegrationTests;

import com.app.labdesoftware.ContainerOfTests;
import com.app.labdesoftware.controllers.product.ProductRequest;
import com.app.labdesoftware.enumerations.StatusStock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerTest extends ContainerOfTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should get a product (Admin)")
    @WithUserDetails("Admin")
    void get_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/{id}", 5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("Should get a product (User)")
    @WithUserDetails("User")
    void get_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/{id}", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products (Admin)")
    @WithUserDetails("Admin")
    void listAll_Admins() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products (User)")
    @WithUserDetails("User")
    void listAll_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by category (Admin)")
    @WithUserDetails("Admin")
    void listByCategory_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/category/{categoryId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by category (User)")
    @WithUserDetails("User")
    void listByCategory_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/category/{categoryId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by price (Admin)")
    @WithUserDetails("Admin")
    void listByPrice_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/price/{price}", 50))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by price (User)")
    @WithUserDetails("User")
    void listByPrice_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/price/{price}", 50))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by brand (Admin)")
    @WithUserDetails("Admin")
    void listByBrand_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/brand/{brand}", "BrandC"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by brand (User)")
    @WithUserDetails("User")
    void listByBrand_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/brand/{brand}", "BrandC"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list all product in stock (Admin)")
    @WithMockUser(roles = {"ADMIN"})
    void listInStock_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/inStock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list all product in stock (User)")
    @WithMockUser()
    void listInStock_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/inStock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list all products out of stock")
    @WithMockUser(roles = {"ADMIN"})
    void listOutOfStock() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/outOfStock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("Should create a new product")
    @WithMockUser(roles = {"ADMIN"})
    void create() throws Exception {
        ProductRequest productRequest = new ProductRequest("IntegrationTest","Integration",1,2, StatusStock.IN_STOCK);
        String jsonRequest = mapper.writeValueAsString(productRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should update a product")
    @WithMockUser(roles = {"ADMIN"})
    void update() throws Exception {
        ProductRequest productRequest = new ProductRequest("UpdateIntegrationTest","UpdateIntegration",50,1, StatusStock.OUT_OF_STOCK);
        String jsonRequest = mapper.writeValueAsString(productRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/product/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should remove the product from stock")
    @WithMockUser(roles = {"ADMIN"})
    void delete() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/product/{id}",5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}