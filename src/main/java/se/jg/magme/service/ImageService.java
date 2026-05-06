package se.jg.magme.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import se.jg.magme.config.AppProperties;
import se.jg.magme.model.Card;
import se.jg.magme.repository.CardRepository;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ImageService {

    private final CardRepository cardRepository;
    private final ScryfallClient scryfallService;
    private final CardService cardService;
    private final AppProperties appProperties;
    private static final Logger logger = Logger.getLogger(ImageService.class.getName());
    public ImageService(CardRepository cardRepository, ScryfallClient scryfallService, CardService cardService, AppProperties appProperties) {
        this.cardRepository = cardRepository;
        this.scryfallService = scryfallService;
        this.cardService = cardService;
        this.appProperties = appProperties;
    }

    public ResponseEntity<byte[]> getCard(UUID id) {
        Card c = cardRepository.getCardById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card record not found"));
        if (!Files.exists(c.getOrgPath(appProperties.getCardImagesPath()))) {
            fetchOrg(c);
        }
        byte[] responseBody;
        try{
            responseBody = Files.readAllBytes(c.getOrgPath(appProperties.getCardImagesPath()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Original card image not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(responseBody);
    }

    private void fetchOrg(Card c) {
        byte[] imgBytes = scryfallService.getCardImage(c.getId());
        try {
            Files.createDirectories(c.getOrgPath(appProperties.getCardImagesPath()).getParent());
            Files.write(c.getOrgPath(appProperties.getCardImagesPath()), imgBytes);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save original card image", new Throwable());
        }
    }

    public ResponseEntity<byte[]> getNoCmc(UUID id) {
        Card c;
        if (id == null) {
            // set testing: 
            List<String> sets = List.of("neo", "8ed", "chk", "rav", "tsp", "lrw", "ala", "zen", "isd", "rtr");
            c = cardService.getRandomCard(sets, null);
        } else {
            c = cardRepository.getCardById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card not found"));
        }
        while (true) {
            createNoCmc(c);
            if (Files.exists(c.getNoCmcPath(appProperties.getCardImagesPath()))) {
                break;
            }
            c = cardService.getRandomCard(null, null);
        }
        byte[] responseBody;
        try{
            responseBody = Files.readAllBytes(c.getNoCmcPath(appProperties.getCardImagesPath()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "cardImg not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(responseBody);
    }

    private void createNoCmc(Card card) {
        Mat orgImg;
        if (!Files.exists(card.getOrgPath(appProperties.getCardImagesPath()))) {
            fetchOrg(card);
        }
        try {
            orgImg = readImage(card.getOrgPath(appProperties.getCardImagesPath()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read image", e);
            return;
        }
        if (orgImg.empty()) {
            logger.log(Level.SEVERE, "Image not found", new Throwable());
            return;
        }
        Mat orgImgTop = orgImg.submat(0, (int)(orgImg.rows() * 0.2), 0, orgImg.cols());
        Mat grayImgTop = new Mat();
        Imgproc.cvtColor(orgImgTop, grayImgTop, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayImgTop, grayImgTop);
        int copyRegionStartX = decidedCopyXVal(card, grayImgTop);

        Rect copySlice = new Rect(copyRegionStartX, card.nameManaRegionTopY(), card.manaRegionStartX() - copyRegionStartX - 3, card.nameManaRegionHeight()); //holding copy area
        Rect stretchSlice = new Rect(copyRegionStartX, card.nameManaRegionTopY(), card.manaRegionEndX() - copyRegionStartX, card.nameManaRegionHeight()); //holding strech area
        Mat result = orgImg.clone();
        Mat copyArea = orgImg.submat(copySlice);
        Mat pasteArea = result.submat(stretchSlice);
        Imgproc.resize(copyArea, pasteArea, pasteArea.size(), 0, 0, Imgproc.INTER_LINEAR);
        Path noCmcPath = Path.of(appProperties.getCardImagesPath(), "nocmc", card.getSetCode(), card.getId() + ".jpg");
        try {
            Files.createDirectories(noCmcPath.getParent());
            Imgcodecs.imwrite(noCmcPath.toString(), result);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save nocmc card image", new Throwable());
        }
    }

    private int decidedCopyXVal(Card card, Mat mat) {
        int guessedNameStartX = guessNameStartX(card, mat);
        int highestAcceptableStartX = card.manaRegionStartX() - card.cmcDiameter();
        int lowestNeededStartX = card.manaRegionStartX() - card.cmcDiameter() * 4;
        if (guessedNameStartX > highestAcceptableStartX) {
            return highestAcceptableStartX;
        }
        return Math.max(guessedNameStartX, lowestNeededStartX);
    }

    private int guessNameStartX(Card card, Mat mat) {
        int safetyMargin = 2;
        int curXStart = card.manaRegionStartX() - card.cmcDiameter() - safetyMargin;
        Rect columnSlice = new Rect(curXStart, card.cmcDiameter(), card.nameManaRegionTopY(), card.nameManaRegionHeight());
        double mean = Core.mean(mat.submat(columnSlice)).val[0]; // mean brightness of a thin area to the left of the leftmost mana circle
        curXStart -= columnSlice.width/2;
        while (curXStart > 0) {
            columnSlice = new Rect(curXStart, columnSlice.y, columnSlice.width, columnSlice.height);
            if (Math.abs(Core.mean(mat.submat(columnSlice)).val[0] - mean) > 40) {
                break;
            }
            curXStart -= columnSlice.width/2;
        }
        return columnSlice.x + columnSlice.width/2;
    }

    private Mat readImage(Path path) throws IOException {
        BufferedImage img = ImageIO.read(path.toFile());
        Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, pixels);
        return mat;
    }
}














