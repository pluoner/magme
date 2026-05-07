package se.jg.magme.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import se.jg.magme.service.GameService;


@Controller
public class PageController {

    private final GameService gameService;

    public PageController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/start";
    }
    
    @GetMapping("/start")
    public String startPage(Model model) {
        model.addAttribute("supportedSets", gameService.getSupportedSets());
        return "start";
    }

    @PostMapping("/game")
    public String startGame(@RequestParam(name = "set", required = false) String set,
        @RequestParam(name = "colors", required = false) List<String> colors,
        @RequestParam(name = "rarities", required = false) List<String> rarities,
        HttpSession session) {
            gameService.newGame(set, colors, rarities, session);
            return "redirect:/game";
    }

    @GetMapping("/game")
    public String gamePage() {
        return "game";
    }
    
}
