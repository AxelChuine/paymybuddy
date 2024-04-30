package com.openclassrooms.paymybuddy.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class AccountDTO {
    private Integer identifier;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String name;
    private Float balance;
    private List<AccountDTO> connections;

}

