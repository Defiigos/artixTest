package com.example.artixtest.controller;

import com.example.artixtest.dto.MessageResponse;
import com.example.artixtest.dto.TransactionDto;
import com.example.artixtest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/return")
    public ResponseEntity<MessageResponse> transactionReturn(
            @RequestParam(value = "transactionId") Long transactionId
    ) {
        return transactionService.returnTransaction(transactionId);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionDto>> getTransactionHistory(
            @RequestParam(value = "cardId") Long cardId
    ) {
        return transactionService.getTransactionHistory(cardId);
    }


}
