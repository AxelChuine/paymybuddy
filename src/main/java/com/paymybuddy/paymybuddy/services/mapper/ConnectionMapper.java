package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.ConnectionDto;
import com.paymybuddy.paymybuddy.models.Connection;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConnectionMapper {
    private final AccountMapper accountMapper;

    public ConnectionMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public ConnectionDto toDto(Connection connection) {
        return new ConnectionDto(
                Objects.nonNull(connection.getAccount()) ? accountMapper.toDto(connection.getAccount()) : null,
                Objects.nonNull(connection.getConnection()) ? accountMapper.toDto(connection.getConnection()) : null
        );
    }
}