package com.example.artixtest.controller;

import com.example.artixtest.dto.MessageResponse;
import com.example.artixtest.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/balance/accrue")
    public ResponseEntity<MessageResponse> accrue(
            @RequestParam(value = "cardId") Long cardId,
            @RequestParam(value = "value") Integer value
    ) {
        return cardService.accrueBalance(cardId, value);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/balance/writeOff")
    public ResponseEntity<MessageResponse> writeOff(
            @RequestParam(value = "cardId") Long cardId,
            @RequestParam(value = "value") Integer value
    ) {
        return cardService.writeOffBalance(cardId, value);
    }

    @GetMapping("/balance")
    public ResponseEntity<MessageResponse> getBalance(
            @RequestParam(value = "cardId") Long cardId
    ) {
        return cardService.getBalance(cardId);
    }


}
