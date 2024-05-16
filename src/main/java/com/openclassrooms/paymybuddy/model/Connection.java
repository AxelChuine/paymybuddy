package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "connection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Connection {
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "account_connection_id")
    private Integer accountConnectionId;
}
