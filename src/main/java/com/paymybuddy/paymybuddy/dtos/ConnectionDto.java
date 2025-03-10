package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDto {
    private AccountVM accountVM;
    private AccountVM connectionVM;

    public String getConnectionEmail() {
        return connectionVM.getEmail();
    }

    public void setConnectionEmail(String connectionEmail) {
        connectionVM.setEmail(connectionEmail);
    }
}