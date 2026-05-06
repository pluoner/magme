package se.jg.magme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import se.jg.magme.model.Card;

@Service
public class GameService {

    private final GameSessionService gameSessionService;
    private final HttpServletRequest httpServletRequest;
    private final CardService cardService;
    private final ImageService imageService;
    public GameService(GameSessionService gameSessionService, HttpServletRequest httpServletRequest, CardService cardService, ImageService imageService) {
        this.gameSessionService = gameSessionService;
        this.httpServletRequest = httpServletRequest;
        this.cardService = cardService;
        this.imageService = imageService;
    }

    public void newGame(String set, List<String> colors, HttpSession session) {
        session.invalidate();
        session = httpServletRequest.getSession(true);
        ArrayList<String> selectedColors = (colors == null) ? new ArrayList<>() : new ArrayList<>(colors);
        String selectedSet = (set == null || set.isBlank()) ? null : set;
        gameSessionService.save(session, new GameSessionService.SearchCriteria(selectedSet, selectedColors));
        List<String> sets = selectedSet == null ? null : List.of(selectedSet);
        Card card = cardService.getRandomCard(sets, selectedColors);
        session.setAttribute("currentCard", card.getId());
        session.setAttribute("previousCards", new ArrayList<UUID>());
        session.setAttribute("currentHighScore", 0);
    }

    public String guessCmc(Integer guessedCmc) {
        if (guessedCmc == null) {
            return "Please provide a CMC value.";
        }
        return "OK";
    }

    public ResponseEntity<byte[]> getCurrentCard() {
        HttpSession session = httpServletRequest.getSession(true);
        UUID currentUUID = gameSessionService.getCurrentCardId(session).orElseThrow(() -> new RuntimeException("No current card found for session " + session.getId()));
        return imageService.getNoCmc(currentUUID);
    }

}














