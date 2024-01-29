package com.openclassrooms.paymybuddy.service.mapper;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IAccountMapper {

    IAccountMapper INSTANCE = Mappers.getMapper(IAccountMapper.class);

    AccountDTO accountToAccountDto (Account account);

    List<AccountDTO> accountsToAccountDTOList (List<Account> accounts);

    Account accountDtoToAccount (AccountDTO accountDTO);

    List<Account> accountDtoListToAccountList (List<AccountDTO> accountDTOS);
}
