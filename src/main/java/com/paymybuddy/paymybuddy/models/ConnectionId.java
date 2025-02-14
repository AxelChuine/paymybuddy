package com.paymybuddy.paymybuddy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
public class ConnectionId implements Serializable {
    private Long account;
    private Long connection;

    public ConnectionId() {
        this.account = 0L;
        this.connection = 0L;
    }

    public ConnectionId(Long account, Long connection) {
        this.account = account;
        this.connection = connection;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getConnection() {
        return connection;
    }

    public void setConnection(Long connection) {
        this.connection = connection;
    }
}
