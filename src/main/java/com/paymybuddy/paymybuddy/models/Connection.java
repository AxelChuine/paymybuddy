package com.paymybuddy.paymybuddy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@IdClass(ConnectionId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Connection {

    @Id
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Id
    @ManyToOne
    @JoinColumn(name = "account_connection_id")
    private Account connection;
}
