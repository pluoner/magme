package se.jg.magme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.jg.magme.service.CardImageService;
import java.util.UUID;

@RestController
@RequestMapping("/img")
public class CardImageController {
    private final CardImageService cardImageService;

    public CardImageController(CardImageService cardImageService) {
        this.cardImageService = cardImageService;
    }

    @GetMapping("/card")
    public ResponseEntity<byte[]> getCard(
            @RequestParam() UUID id
    ) {
        return cardImageService.getCardJpg(id);
    }

    @GetMapping("/nocmc")
    public ResponseEntity<byte[]> getNoCmcCard(
            @RequestParam(required = false) UUID id
    ) {
        return cardImageService.getNoCmcCard(id);
    }
}
