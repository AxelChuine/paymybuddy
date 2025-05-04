package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.services.AccountService;
import com.paymybuddy.paymybuddy.services.ConnectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
public class ConnectionControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ConnectionController controller;

    @Mock
    private ConnectionService service;

    @Mock
    private AccountService accountService;

    private final Long accountId = 1L;
    private final Long connectionId = 2L;
    private final String username = "username";
    private final String email = "email";
    private final String password = "password";
    private final String name = "name";

    private Account account;
    private Account connectionAccount;
    private Connection connectionDto;

    @BeforeEach
    public void setup() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(controller).build();
        this.account = new Account(
                this.accountId,
                this.username,
                this.password,
                this.email,
                this.name,
                null,
                null
        );
        this.connectionAccount = new Account(
                this.connectionId,
                this.username,
                this.password,
                this.email,
                this.name,
                null,
                null
        );
        this.connectionDto = new Connection(this.account, this.connectionAccount);
    }

    @Test
    public void connectionShouldReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/connection/connection"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
                .andExpect(MockMvcResultMatchers.view().name("connection/connection"));
    }

    @Test
    public void createConnectionShouldReturnHttpStatusOk() throws Exception {
        Mockito.when(this.accountService.getAccount()).thenReturn(this.account);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/connection/connection")
                .param("email", this.connectionAccount.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
                .andExpect(MockMvcResultMatchers.view().name("connection/connection"));
    }
}
