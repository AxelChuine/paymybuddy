package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.ConnectionDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.models.Connection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ConnectionMapper {
    private final AccountMapper accountMapper;

    public ConnectionMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public ConnectionDto toDto(Connection connection) {
        return new ConnectionDto(
                Objects.nonNull(connection.getAccount()) ? accountMapper.toAccountVM(connection.getAccount()) : null,
                Objects.nonNull(connection.getConnection()) ? accountMapper.toAccountVM(connection.getConnection()) : null
        );
    }

    public ConnectionVM toVM(Connection connection) {
        return new ConnectionVM(
                connection.getAccount().getIdentifier(),
                connection.getConnection().getIdentifier(),
                Objects.nonNull(connection.getConnection()) ? connection.getConnection().getFirstName() : null,
                Objects.nonNull(connection.getConnection()) ? connection.getConnection().getLastName() : null
        );
    }

    public List<ConnectionVM> toVMList(List<Connection> accountList) {
        List<ConnectionVM> connectionVMList = new ArrayList<>();
        for (Connection connection : accountList) {
            ConnectionVM connectionVM = toVM(connection);
            connectionVMList.add(connectionVM);
        }
        return connectionVMList;
    }
}