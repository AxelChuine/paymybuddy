package com.paymybuddy.paymybuddy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@IdClass(ConnectionId.class)
@EqualsAndHashCode
public class Connection {

    @Id
    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

    @Id
    @ManyToOne
    @JoinColumn(name = "connection")
    private Account connection;

    public Connection() {
        this.account = new Account();
        this.connection = new Account();
    }

    public Connection(final Account account, final Account connection) {
        this.account = account;
        this.connection = connection;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Account getConnection() {
        return connection;
    }

    public void setConnection(final Account connection) {
        this.connection = connection;
    }
}
