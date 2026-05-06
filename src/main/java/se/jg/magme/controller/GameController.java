package se.jg.magme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import se.jg.magme.service.GameService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/game")
public class GameController {

    record GuessResponse(String status, String message, String failedCardId) {}

    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/guessedCmc")
    public GuessResponse postGuessedCmc(@RequestBody Integer cmc, HttpSession session) {
        String[] result = gameService.guessCmc(cmc, session);
        return new GuessResponse(result[0], result[1], result.length > 2 ? result[2] : null);
    }
    
    @GetMapping("/getCurrentCard")
    public ResponseEntity<byte[]> getNoCmcCard(HttpSession session) {
        return gameService.getCurrentCard(session);
    }

}
