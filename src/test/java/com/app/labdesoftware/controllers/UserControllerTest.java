package com.app.labdesoftware.controllers;

import com.app.labdesoftware.ContainerOfTests;
import com.app.labdesoftware.controllers.user.RegisterRequest;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.UserService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest extends ContainerOfTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

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
    @DisplayName("Should get a list of all users")
    @WithMockUser(username = "Admin",password = "Admin", roles = {"ADMIN"})
    void listAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all")
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all users with role Admin")
    @WithMockUser(roles = {"ADMIN"})
    void listAllAdmins() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/admins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Should get a list of all users with role User")
    @WithMockUser(roles = {"ADMIN"})
    void listAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should register a new Admin")
    @WithMockUser(roles = {"ADMIN"})
    void registerAdmin() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("NewAdmin","NewAdmin");
        String jsonRequest = mapper.writeValueAsString(registerRequest);

        Mockito.when(userService.registerAdmin(any(User.class),any(RegisterRequest.class))).thenReturn(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("Should update a user")
    @WithMockUser(roles = {"ADMIN"})
    void update() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("UpdatedAdmin","UpdatedAdmin");

        mockMvc.perform(MockMvcRequestBuilders.put("user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}