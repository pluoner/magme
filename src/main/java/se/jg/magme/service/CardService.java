package se.jg.magme.service;

import org.springframework.beans.factory.BeanRegistry.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.jg.magme.model.Card;
import se.jg.magme.repository.CardRepository;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CardService {

    private final Random rand = new Random();

    private final CardRepository cardRepository;
    private static final Logger logger = Logger.getLogger(CardImageService.class.getName());
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getRandomCard(List<String> sets, List<String> colors) {
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
        Specification<Card> manaCostSpec = (root, query, cb) -> cb.and(cb.isNotNull(root.get("manaCost")), cb.notEqual(root.get("manaCost"), ""));
        Specification<Card> totSpec = Specification.where(setSpec).and(colorSpec).and(typeSpec).and(manaCostSpec);
        List<Card> res = cardRepository.findAll(totSpec);
        if (res.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No card matching criteria found");
        }
        int randIndex = rand.nextInt(res.size());
        Card c = res.get(randIndex);
        logger.log(Level.INFO, () -> "Random card: " + c.getName() + ", set: " + c.getSetCode() + ", oracleID: " + c.getOracleID());
        return c;
    }
}














