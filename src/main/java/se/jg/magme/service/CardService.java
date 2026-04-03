package se.jg.magme.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.jg.magme.model.Card;
import se.jg.magme.repository.CardRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

@Service
public class CardService {

    @Value("${app.card-images-path}")
    private String cardImagesPath;

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getRandomCard(List<String> sets, List<String> colors, List<String> types) {
        Specification<Card> setSpec = (root, query, cb) -> {
            if (sets == null) {
                return null;
            }
            return root.get("setCode").in(sets);
        };
        Specification<Card> colorSpec = (root, query, cb) -> {
            if (colors == null) {
                return null;
            }
            return root.get("colors").in(colors);
        };
        Specification<Card> typeSpec = (root, query, cb) -> {
            if (types == null) {
                return null;
            }
            return root.get("typeLine").in(types);
        };

        Specification<Card> totSpec = Specification.where(setSpec).and(colorSpec).and(typeSpec);
        List<Card> res = cardRepository.findAll(totSpec);
        if (res.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No card matching criteria found");
        }
        Random rand = new Random();
        return res.get(rand.nextInt(res.size()));
    }

    public ResponseEntity<byte[]> getCardJpg(String scryfallID) {
        Card c = cardRepository.getCardByScryfallID(scryfallID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card not found"));
        Path jpgPath = Path.of(cardImagesPath, c.getSetCode(), c.getScryfallID() + ".jpg");
        byte[] responseBody;
        try{
            responseBody = Files.readAllBytes(jpgPath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "cardImg not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(responseBody);
    }
}