package se.jg.magme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class GameSessionService {
    public static final String SEARCH_CRITERIA_KEY = "searchCriteria";
    public static final String PREVIOUS_CARDS_KEY = "previousCards";
    public static final String CURRENT_HIGH_SCORE_KEY = "currentHighScore";
    public static final String CURRENT_CARD_KEY = "currentCard";

    public void save(HttpSession session, SearchCriteria criteria) {
        criteria = new SearchCriteria(
            (criteria.set() == null || criteria.set().isBlank()) ? null : criteria.set(),
            criteria.colors() == null ? new ArrayList<>() : new ArrayList<>(criteria.colors()),
            criteria.rarities() == null ? new ArrayList<>() : new ArrayList<>(criteria.rarities())
        );
        session.setAttribute(SEARCH_CRITERIA_KEY, criteria);
    }

    public Optional<UUID> getCurrentCardId(HttpSession session) {
        return Optional.ofNullable((UUID) session.getAttribute("currentCard"));
    }

    public SearchCriteria get(HttpSession session) {
        return (SearchCriteria) session.getAttribute(SEARCH_CRITERIA_KEY);
    }

    public Optional<SearchCriteria> getSearchCriteria(HttpSession session) {
        return Optional.ofNullable(get(session));
    }

    public record SearchCriteria(String set, List<String> colors, List<String> rarities) implements java.io.Serializable {
    }
}