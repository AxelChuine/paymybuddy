package com.openclassrooms.paymybuddy.service.mapper;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.service.dto.ConnectionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IConnectionMapper {
    IConnectionMapper INSTANCE = Mappers.getMapper(IConnectionMapper.class);

    ConnectionDto connectionToConnectionDto(Connection connection);

    Connection connectionDtoToConnection(ConnectionDto connectionDto);

    List<Connection> connectionDtoToConnectionList(List<ConnectionDto> connectionDtoList);

    List<ConnectionDto> connectionListToConnectionDtoList(List<Connection> connectionList);
}
