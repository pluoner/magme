package se.jg.magme.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.jg.magme.model.Card;
import se.jg.magme.repository.CardRepository;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CardService {

    private final Random rand = new Random();

    private final CardRepository cardRepository;
    private static final Logger logger = Logger.getLogger(CardService.class.getName());
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
    public Card getCardById(UUID id) {
        return cardRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card with id " + id + " not found")
        );
    }

    public Card getRandomCard(List<String> sets, List<String> colors) {
        return getRandomCard(sets, colors, null);
    }
    public Card getRandomCard(List<String> sets, List<String> colors, List<UUID> excludeIds) {
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
        List<String> types = List.of("Creature", "Planeswalker", "Artifact", "Enchantment", "Instant", "Sorcery");
        Specification<Card> typeSpec = (root, query, cb) -> {
            if (types == null) {
                return null;
            }
            return root.get("typeLine").in(types);
        };
        Specification<Card> excludeSpec = (root, query, cb) -> {
            if (excludeIds == null || excludeIds.isEmpty()) {
                return null;
            }
            return cb.not(root.get("id").in(excludeIds));
        };
        Specification<Card> manaCostSpec = (root, query, cb) -> cb.and(cb.isNotNull(root.get("manaCost")), cb.notEqual(root.get("manaCost"), ""));
        Specification<Card> totSpec = Specification.where(setSpec).and(colorSpec).and(typeSpec).and(excludeSpec).and(manaCostSpec);
        List<Card> res = cardRepository.findAll(totSpec);
        if (res.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No card matching criteria found");
        }
        int randIndex = rand.nextInt(res.size());
        Card c = res.get(randIndex);
        logger.log(Level.INFO, () -> "Random card: " + c.getName() + ", set: " + c.getSetCode() + ", id: " + c.getId());
        return c;
    }
}














