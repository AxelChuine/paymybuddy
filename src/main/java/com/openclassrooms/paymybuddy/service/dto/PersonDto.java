package com.openclassrooms.paymybuddy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDto {
    private Integer identifier;

    private String firstName;

    private String lastName;

    private String password;
}
