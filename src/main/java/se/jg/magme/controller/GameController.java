package se.jg.magme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jg.magme.service.GameService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/guessedCmc")
    public String postGuessedCmc(@RequestBody Integer cmc) {
        record GuessRespons(String message) {}
        GuessRespons response = new GuessRespons(gameService.guessCmc(cmc));
        return response.message();
    }
    @GetMapping("/getCurrentCard")
    public ResponseEntity<byte[]> getNoCmcCard(
    ) {
        return gameService.getCurrentCard();
    }

}
