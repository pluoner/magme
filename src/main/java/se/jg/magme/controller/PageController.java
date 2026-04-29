package se.jg.magme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String root() {
        return "redirect:/start";
    }
    
    @GetMapping("/start")
    public String startPage() {
        return "start";
    }
}
