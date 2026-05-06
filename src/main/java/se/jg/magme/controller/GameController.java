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

    record GuessRespons(String status, String message) {}

    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/guessedCmc")
    public GuessRespons postGuessedCmc(@RequestBody Integer cmc, HttpSession session) {
        String[] result = gameService.guessCmc(cmc, session);
        return new GuessRespons(result[0], result[1]);
    }
    
    @GetMapping("/getCurrentCard")
    public ResponseEntity<byte[]> getNoCmcCard(HttpSession session) {
        return gameService.getCurrentCard(session);
    }

}
