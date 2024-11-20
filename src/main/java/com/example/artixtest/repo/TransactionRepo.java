package com.example.artixtest.repo;

import com.example.artixtest.model.Card;
import com.example.artixtest.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCard(Card card);
}
