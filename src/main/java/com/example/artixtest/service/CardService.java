package com.example.artixtest.service;

import com.example.artixtest.dto.MessageResponse;
import com.example.artixtest.model.Transaction;
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
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CardService {

    @Value("${hostname}")
    private String uri;

    private final CardRepo cardRepo;
    private final TransactionRepo transactionRepo;

    @Transactional
    public ResponseEntity<MessageResponse> accrueBalance(Long cardId, Integer value) {

        val card = cardRepo.findById(cardId).orElseThrow(EntityNotFoundException::new);
        card.setBalance(card.getBalance() + value);

        val transaction = new Transaction() {{
            setCard(card);
            setType(TransactionType.INCOME);
            setDateTime(LocalDateTime.now());
            setTransactionValue(value);
        }};

        card.addTransaction(transaction);
        cardRepo.save(card);

        return ResponseEntity.created(URI.create(uri + "/card/balance/accrue"))
                .body(new MessageResponse("balance successfully update"));
    }

    @Transactional
    public ResponseEntity<MessageResponse> writeOffBalance(Long cardId, Integer value) {

        val card = cardRepo.findById(cardId).orElseThrow(EntityNotFoundException::new);
        val cardBalance = card.getBalance();

        if (cardBalance < value) {
            throw new IllegalArgumentException("balance must be greater than written off value");
        }

        card.setBalance(cardBalance - value);

        val transaction = new Transaction() {{
            setCard(card);
            setType(TransactionType.OUTCOME);
            setDateTime(LocalDateTime.now());
            setTransactionValue(value);
        }};
        card.addTransaction(transaction);
        cardRepo.save(card);

        return ResponseEntity.created(URI.create(uri + "/balance/writeOff"))
                .body(new MessageResponse("balance successfully update"));
    }
    public ResponseEntity<MessageResponse> getBalance(Long cardId) {

        val card = cardRepo.findById(cardId).orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.created(URI.create(uri + "/card/balance"))
                .body(new MessageResponse(String.format("balance by card with id:%s is %s", cardId, card.getBalance())));
    }
}
