package com.example.artixtest.service;

import com.example.artixtest.dto.MessageResponse;
import com.example.artixtest.dto.TransactionDto;
import com.example.artixtest.model.enums.TransactionType;
import com.example.artixtest.repo.CardRepo;
import com.example.artixtest.repo.TransactionRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Value("${hostname}")
    private String uri;

    private final TransactionRepo transactionRepo;
    private final CardRepo cardRepo;

    @Transactional
    public ResponseEntity<MessageResponse> returnTransaction(Long transactionId) {

        val transaction = transactionRepo.findById(transactionId).orElseThrow(EntityNotFoundException::new);

        if (transaction.getType() == TransactionType.REFUND) {
            throw new IllegalArgumentException("transaction already refund");
        }

        val card = cardRepo.findById(transaction.getCard().getId()).orElseThrow(EntityNotFoundException::new);

        switch (transaction.getType()) {
            case INCOME -> {
                card.setBalance(card.getBalance() - transaction.getTransactionValue());
            }
            case OUTCOME -> {
                card.setBalance(card.getBalance() + transaction.getTransactionValue());
            }
        }
        cardRepo.save(card);

        transaction.setType(TransactionType.REFUND);
        transactionRepo.save(transaction);

        return ResponseEntity.created(URI.create(uri + "/transaction/return"))
                .body(new MessageResponse("transaction successfully return"));
    }

    public ResponseEntity<List<TransactionDto>> getTransactionHistory(Long cardId) {
        val card = cardRepo.findById(cardId).orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.ok(transactionRepo.findAllByCard(card)
                .stream().map(TransactionDto::build)
                .collect(Collectors.toList()));
    }
}
