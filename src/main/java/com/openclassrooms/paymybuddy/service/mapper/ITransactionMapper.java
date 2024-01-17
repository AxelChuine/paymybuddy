package com.openclassrooms.paymybuddy.service.mapper;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ITransactionMapper {

    ITransactionMapper INSTANCE = Mappers.getMapper(ITransactionMapper.class);

    TransactionDTO transactionToTransactionDto (Transaction transaction);

    List<TransactionDTO> transactionsToTransactionsDtos (List<Transaction> transactions);

    Transaction transactionDtoToTransaction (TransactionDTO transactionDTO);

    List<Transaction> transactionDtoToTransaction (List<TransactionDTO> transactionDTOS);
}
