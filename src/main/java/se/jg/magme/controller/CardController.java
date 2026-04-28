package se.jg.magme.controller;

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

    @GetMapping("/all")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/byid")
    public Card getCardById(@RequestParam UUID id) {
        return cardService.getCardById(id);
    }

    @GetMapping("/random")
    public Card getRandom(
        @RequestParam(required = false) List<String> set,
        @RequestParam(required = false) List<String> color
    ) {
        return cardService.getRandomCard(set, color);
    }
}