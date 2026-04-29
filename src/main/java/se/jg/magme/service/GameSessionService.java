package se.jg.magme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class GameSessionService {
    public static final String SEARCH_CRITERIA_KEY = "searchCriteria";

    public void save(HttpSession session, SearchCriteria criteria) {
        criteria = new SearchCriteria(
            (criteria.set() == null || criteria.set().isBlank()) ? null : criteria.set(),
            criteria.colors() == null ? new ArrayList<>() : new ArrayList<>(criteria.colors())
        );
        session.setAttribute(SEARCH_CRITERIA_KEY, criteria);
    }

    public SearchCriteria get(HttpSession session) {
        return (SearchCriteria) session.getAttribute(SEARCH_CRITERIA_KEY);
    }

    public record SearchCriteria(String set, List<String> colors) implements java.io.Serializable {
    }
}