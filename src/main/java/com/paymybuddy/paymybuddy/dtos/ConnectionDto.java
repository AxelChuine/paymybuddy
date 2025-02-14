package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
public class ConnectionDto {
    private AccountVM accountVM;
    private AccountVM connectionVM;

    public ConnectionDto() {
        this.accountVM = new AccountVM();
        this.connectionVM = new AccountVM();
    }

    public ConnectionDto(AccountVM accountVM, AccountVM connectionVM) {
        this.accountVM = accountVM;
        this.connectionVM = connectionVM;
    }

    public AccountVM getAccountVM() {
        return accountVM;
    }

    public void setAccountVM(AccountVM accountVM) {
        this.accountVM = accountVM;
    }

    public AccountVM getConnectionVM() {
        return connectionVM;
    }

    public void setConnectionVM(AccountVM connectionVM) {
        this.connectionVM = connectionVM;
    }
}