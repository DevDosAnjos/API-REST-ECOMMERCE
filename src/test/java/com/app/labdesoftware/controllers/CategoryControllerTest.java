package com.app.labdesoftware.controllers;

import com.app.labdesoftware.ContainerOfTests;
import com.app.labdesoftware.controllers.category.CategoryRequest;
import com.app.labdesoftware.enumerations.StatusCategory;
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
class CategoryControllerTest extends ContainerOfTests {

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
    @DisplayName("Should get a category")
    @WithMockUser(roles = {"ADMIN"})
    void get() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/{id}","Electronics"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should get a list of all categories")
    @WithMockUser(roles = {"ADMIN"})
    void listAll() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/category/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all active categories")
    @WithMockUser(roles = {"ADMIN"})
    void listActive() throws Exception {
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

        mockMvc
                .perform(MockMvcRequestBuilders.post("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should update a category")
    @WithMockUser(roles = {"ADMIN"})
    void update() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("UpdateIntegrationTest", StatusCategory.ACTIVE);

        mockMvc
                .perform(MockMvcRequestBuilders.put("/category/{id}", "IntegrationTest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should disable a category")
    @WithMockUser(roles = {"ADMIN"})
    void delete() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/category/{id}","UpdateIntegrationTest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}