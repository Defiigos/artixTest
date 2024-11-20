package com.example.artixtest.dto;

import com.example.artixtest.model.Transaction;
import com.example.artixtest.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    private LocalDateTime dateTime;
    private Integer transactionValue;
    private TransactionType type;
    private Long cardId;

    public static TransactionDto build(Transaction transaction){
        return new TransactionDto(
                transaction.getId(),
                transaction.getDateTime(),
                transaction.getTransactionValue(),
                transaction.getType(),
                transaction.getCard().getId()
        );
    }
}