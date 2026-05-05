package se.jg.magme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class GameService {

    private final GameSessionService gameSessionService;
    private final HttpServletRequest httpServletRequest;

    public GameService(GameSessionService gameSessionService, HttpServletRequest httpServletRequest) {
        this.gameSessionService = gameSessionService;
        this.httpServletRequest = httpServletRequest;
    }

    public void newGame(String set, List<String> colors, HttpSession session) {
        session.invalidate();
        session = httpServletRequest.getSession(true);
        ArrayList<String> selectedColors = (colors == null) ? new ArrayList<>() : new ArrayList<>(colors);
        String selectedSet = (set == null || set.isBlank()) ? null : set;
        gameSessionService.save(session, new GameSessionService.SearchCriteria(selectedSet, selectedColors));
    }
}














