package se.jg.magme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.jg.magme.service.ImageService;
import java.util.UUID;

@RestController
@RequestMapping("/img")
public class ImageController {
    private final ImageService cardImageService;

    public ImageController(ImageService cardImageService) {
        this.cardImageService = cardImageService;
    }

    @GetMapping("/card")
    public ResponseEntity<byte[]> getCard(
            @RequestParam(name = "id") UUID id
    ) {
        return cardImageService.getCard(id);
    }

    @GetMapping("/nocmc")
    public ResponseEntity<byte[]> getNoCmcCard(
            @RequestParam(name = "id", required = false) UUID id
    ) {
        return cardImageService.getNoCmc(id);
    }
}
