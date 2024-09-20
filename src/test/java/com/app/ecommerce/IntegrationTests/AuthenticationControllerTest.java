package com.app.ecommerce.IntegrationTests;

import com.app.ecommerce.ContainerOfTests;
import com.app.ecommerce.controllers.authentication.LoginRequest;
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
class AuthenticationControllerTest extends ContainerOfTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should login the user")
    void login_User() throws Exception {
        LoginRequest loginRequest = new LoginRequest("User", "User");
        String jsonRequest = mapper.writeValueAsString(loginRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should login the admin")
    void login_Admin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("Admin", "Admin");
        String jsonRequest = mapper.writeValueAsString(loginRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should register a new user")
    void register() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("NewUser","NewUser");
        String jsonRequest = mapper.writeValueAsString(registerRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should register a new Admin")
    @WithUserDetails("Admin")
    void registerAdmin() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("NewAdmin","NewAdmin");
        String jsonRequest = mapper.writeValueAsString(registerRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}