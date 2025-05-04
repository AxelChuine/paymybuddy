package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private HomeController controller;

    @Mock
    private AccountService accountService;

    private final Long accountId = 1L;
    private final String username = "username";
    private final String email = "email";
    private final String password = "password";
    private final String name = "name";

    private Account account;

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
    }

    @Test
    public void findAccountShouldReturnHttpStatusOk() throws Exception {
        Mockito.when(this.accountService.getAccount()).thenReturn(this.account);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/home/settings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
                .andExpect(MockMvcResultMatchers.view().name("home/settings"));
    }

    @Test
    public void createAccountShouldReturnHttpStatusOk () throws Exception {
        Mockito.when(this.accountService.save(Mockito.any(Account.class))).thenReturn(this.account);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/home/settings")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("identifier", this.accountId.toString())
                .param("username", this.username)
                .param("password", this.password)
                .param("email", this.email)
                .param("name", this.name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
                .andExpect(MockMvcResultMatchers.view().name("home/settings"));
    }

    @Test
    public void loginShouldReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/home/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
                .andExpect(MockMvcResultMatchers.view().name("home/login"));
    }

    @Test
    public void loginPostShouldReturnHttpStatusOk() throws Exception {
        Mockito.when(this.accountService.findByUsernameAndPassword(this.account.getEmail(), this.account.getPassword())).thenReturn(this.account);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/home/login")
                .param("email", this.account.getEmail())
                .param("password", this.account.getPassword()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/transaction/transaction"));
    }

    @Test
    public void loginPostShouldReturnHttpStatusOkWhenAccountNotFound() throws Exception {
        Mockito.when(this.accountService.findByUsernameAndPassword(this.account.getEmail(), this.account.getPassword())).thenReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/home/login")
                        .param("email", this.account.getEmail())
                        .param("password", this.account.getPassword()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.view().name("home/login"));
    }
}
