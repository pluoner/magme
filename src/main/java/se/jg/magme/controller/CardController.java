package se.jg.magme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.jg.magme.model.Card;
import se.jg.magme.service.CardService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/allcards")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/randomcard")
    public Card getRandom(
        @RequestParam(required = false) List<String> set,
        @RequestParam(required = false) List<String> color,
        @RequestParam(required = false) List<String> type
    ) {
        return cardService.getRandomCard(set, color, type);
    }

    @GetMapping("/getcardpic")
    public ResponseEntity<byte[]> getCard(
            @RequestParam() UUID id
    ) {
        return cardService.getCardJpg(id);
    }

    @GetMapping("/getrandomcardpic")
    public ResponseEntity<byte[]> getRandomCard(
    ) {
        return cardService.getRandomCardJpg();
    }

    @GetMapping("/getnoctccard")
    public ResponseEntity<byte[]> getNoCTCCard(
            @RequestParam(required = false) UUID id
    ) {
        return cardService.getNoCTCCard(id);
    }
}