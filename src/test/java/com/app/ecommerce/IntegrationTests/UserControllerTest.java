package com.app.ecommerce.IntegrationTests;

import com.app.ecommerce.ContainerOfTests;
import com.app.ecommerce.controllers.user.RegisterRequest;
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
class UserControllerTest extends ContainerOfTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should get a list of all users")
    @WithUserDetails("Admin")
    void listAll() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all users with role Admin")
    @WithUserDetails("Admin")
    void listAllAdmins() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/admins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all users with role User")
    @WithUserDetails("Admin")
    void listAllUsers() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should update a Admin")
    @WithUserDetails("Admin2")
    void update_Admin() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("UpdatedAdminTest","UpdatedAdminTest");
        String jsonRequest = mapper.writeValueAsString(registerRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should update a user")
    @WithUserDetails("User2")
    void update_User() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("UpdatedUserTest","UpdatedUserTest");
        String jsonRequest = mapper.writeValueAsString(registerRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}