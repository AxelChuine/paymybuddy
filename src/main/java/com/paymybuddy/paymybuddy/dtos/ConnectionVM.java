package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
public class ConnectionVM {
    private Long accountId;
    private Long connectionId;

    public ConnectionVM() {
        this.accountId = 0L;
        this.connectionId = 0L;
    }

    public ConnectionVM(Long accountId, Long connectionId) {
        this.accountId = accountId;
        this.connectionId = connectionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }
}
