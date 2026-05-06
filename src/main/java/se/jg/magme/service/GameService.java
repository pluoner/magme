package se.jg.magme.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;
import se.jg.magme.model.Card;

@Service
public class GameService {

    private final GameSessionService gameSessionService;
    private final CardService cardService;
    private final ImageService imageService;
    public GameService(GameSessionService gameSessionService, CardService cardService, ImageService imageService) {
        this.gameSessionService = gameSessionService;
        this.cardService = cardService;
        this.imageService = imageService;
    }

    public void newGame(String set, List<String> colors, HttpSession session) {
        Collections.list(session.getAttributeNames()).forEach(session::removeAttribute);
        ArrayList<String> selectedColors = (colors == null) ? new ArrayList<>() : new ArrayList<>(colors);
        String selectedSet = (set == null || set.isBlank()) ? null : set;
        gameSessionService.save(session, new GameSessionService.SearchCriteria(selectedSet, selectedColors));
        Card card = setNextCard(session);
        session.setAttribute(GameSessionService.CURRENT_CARD_KEY, card.getId());
        session.setAttribute(GameSessionService.PREVIOUS_CARDS_KEY, new ArrayList<UUID>());
        session.setAttribute(GameSessionService.CURRENT_HIGH_SCORE_KEY, 0);
    }

    public String[] guessCmc(Integer guessedCmc, HttpSession session) {
        if (guessedCmc == null) {
            return new String[]{"ERROR", "Please provide a CMC value."};
        }
        UUID currentUUID = gameSessionService.getCurrentCardId(session).orElseThrow(
            () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "No current card found for session " + session.getId()));
        Card currentCard = cardService.getCardById(currentUUID);
        double currentCmcDouble = currentCard.getCmc();
        int currentCmc = (int) Math.round(currentCmcDouble);
        List<UUID> previousCards = getPreviousCards(session).orElseGet(ArrayList::new);
        previousCards.add(currentUUID);
        session.setAttribute("previousCards", previousCards);
        setNextCard(session);
        if (guessedCmc == currentCmc) {
            int newScore = (int) session.getAttribute(GameSessionService.CURRENT_HIGH_SCORE_KEY) + 1;
            session.setAttribute(GameSessionService.CURRENT_HIGH_SCORE_KEY, newScore);
            return new String[]{"OK", "Correct! Your score is now " + newScore + "."};
        } else {
            int currentHighScore = (int) session.getAttribute(GameSessionService.CURRENT_HIGH_SCORE_KEY);
            session.setAttribute(GameSessionService.CURRENT_HIGH_SCORE_KEY, 0);
            return new String[]{"GAMEOVER", "Wrong! The correct CMC was " + currentCmc + ". Your score has been reset to 0. You had a streak of " + currentHighScore + ". Try again!", currentUUID.toString()};
        }
    }

    private Card setNextCard(HttpSession session) {
        List<UUID> excludeIds = getPreviousCards(session).orElseGet(ArrayList::new);
        String set = gameSessionService.getSearchCriteria(session).map(GameSessionService.SearchCriteria::set).orElse(null);
        List<String> colors = gameSessionService.getSearchCriteria(session).map(GameSessionService.SearchCriteria::colors).orElse(null);
        List<String> sets = set == null ? null : List.of(set);
        Card card = cardService.getRandomCard(sets, colors, excludeIds);
        session.setAttribute(GameSessionService.CURRENT_CARD_KEY, card.getId());
        return card;
    }

    public ResponseEntity<byte[]> getCurrentCard(HttpSession session) {
        UUID currentUUID = gameSessionService.getCurrentCardId(session).orElseThrow(() -> new RuntimeException("No current card found for session " + session.getId()));
        return imageService.getNoCmc(currentUUID);
    }

    private Optional<List<UUID>> getPreviousCards(HttpSession session) {
        Object raw = session.getAttribute(GameSessionService.PREVIOUS_CARDS_KEY);
        if (!(raw instanceof List<?> list)) {
            return Optional.empty();
        }
        return Optional.of(list.stream().map(UUID.class::cast).collect(java.util.stream.Collectors.toCollection(ArrayList::new)));
    }
}














