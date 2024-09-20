package com.app.ecommerce.IntegrationTests;

import com.app.ecommerce.ContainerOfTests;
import com.app.ecommerce.controllers.category.CategoryRequest;
import com.app.ecommerce.enumerations.StatusCategory;
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
class CategoryControllerTest extends ContainerOfTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should get a category (Admin)")
    @WithUserDetails("Admin")
    void get_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/{id}",5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a category (User)")
    @WithUserDetails("User")
    void get_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all categories (Admin)")
    @WithUserDetails("Admin")
    void listAll_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all categories (User)")
    @WithUserDetails("User")
    void listAll_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all active categories (Admin)")
    @WithMockUser(roles = {"ADMIN"})
    void listActive_Admin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/active"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all active categories (User)")
    @WithMockUser()
    void listActive_User() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/active"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all inactive categories")
    @WithMockUser(roles = {"ADMIN"})
    void listInactive() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/inactive"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should create a new category")
    @WithMockUser(roles = {"ADMIN"})
    void create() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("IntegrationTest", StatusCategory.ACTIVE);
        String jsonRequest = mapper.writeValueAsString(categoryRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should update a category")
    @WithMockUser(roles = {"ADMIN"})
    void update() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("UpdateIntegrationTest", StatusCategory.ACTIVE);
        String jsonRequest = mapper.writeValueAsString(categoryRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/category/{id}",  5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should disable a category")
    @WithMockUser(roles = {"ADMIN"})
    void delete() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/category/{id}",5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}