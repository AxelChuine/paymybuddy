package com.openclassrooms.paymybuddy.controller.impl;

import com.openclassrooms.paymybuddy.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PersonControllerImpl.class)
public class PersonControllerImplTest {

    @Autowired
    private PersonControllerImpl controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService service;


    @Test
    public void findAllShouldCallFindAllPath () throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/person/all"))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk());
    }
}
