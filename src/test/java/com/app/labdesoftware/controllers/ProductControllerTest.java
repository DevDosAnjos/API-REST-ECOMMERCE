package com.app.labdesoftware.controllers;

import com.app.labdesoftware.ContainerOfTests;
import com.app.labdesoftware.controllers.product.ProductRequest;
import com.app.labdesoftware.enumerations.StatusStock;
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

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerTest extends ContainerOfTests {

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
    @DisplayName("Should get a product")
    @WithMockUser(roles = {"ADMIN"})
    void get() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products")
    @WithMockUser(roles = {"ADMIN"})
    void listAll() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by category")
    @WithMockUser(roles = {"ADMIN"})
    void listByCategory() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/category/{categoryId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by price")
    @WithMockUser(roles = {"ADMIN"})
    void listByPrice() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/price/{price}", 50))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should return a list of all products by brand")
    @WithMockUser(roles = {"ADMIN"})
    void listByBrand() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/brand/{brand}", "BrandC"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list all product in stock")
    @WithMockUser(roles = {"ADMIN"})
    void listInStock() throws Exception {
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
        ProductRequest productRequest = new ProductRequest("IntegrationTest","Integration",1,1, StatusStock.IN_STOCK);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should update a product")
    @WithMockUser(roles = {"ADMIN"})
    void update() throws Exception {
        ProductRequest productRequest = new ProductRequest("UpdateIntegrationTest","UpdateIntegration",1,1, StatusStock.IN_STOCK);

        mockMvc
                .perform(MockMvcRequestBuilders.put("/product/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON))
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