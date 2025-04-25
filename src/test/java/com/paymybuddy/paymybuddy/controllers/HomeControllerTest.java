package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
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
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String username = "username";
    private final String email = "email";
    private final String password = "password";
    private final String name = "name";

    private AccountDto accountDto;

    @BeforeEach
    public void setup() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(controller).build();
        this.accountDto = new AccountDto(
                this.accountId,
                this.firstName,
                this.lastName,
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
        Mockito.when(this.accountService.getAccountDto()).thenReturn(this.accountDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/home/settings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
                .andExpect(MockMvcResultMatchers.view().name("home/settings"));
    }

    @Test
    public void createAccountShouldReturnHttpStatusOk () throws Exception {
        Mockito.when(this.accountService.save(Mockito.any(AccountDto.class))).thenReturn(this.accountDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/home/settings")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("identifier", this.accountId.toString())
                .param("firstName", this.firstName)
                .param("lastName", this.lastName)
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
}
