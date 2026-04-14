package se.jg.magme.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.jg.magme.model.Card;
import se.jg.magme.repository.CardRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CardService {

    @Value("${app.card-images-path}")
    private String cardImagesPath;

    private final CardRepository cardRepository;
    private final ScryfallClient scryfallService;
    private static final Logger logger = Logger.getLogger(CardService.class.getName());
    public CardService(CardRepository cardRepository, ScryfallClient scryfallService) {
        this.cardRepository = cardRepository;
        this.scryfallService = scryfallService;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getRandomCard(List<String> sets, List<String> colors, List<String> types) {
        Specification<Card> setSpec = (root, query, cb) -> {
            if (sets == null) {
                return null;
            }
            return root.get("setCode").in(sets);
        };
        Specification<Card> colorSpec = (root, query, cb) -> {
            if (colors == null) {
                return null;
            }
            return root.get("colors").in(colors);
        };
        Specification<Card> typeSpec = (root, query, cb) -> {
            if (types == null) {
                return null;
            }
            return root.get("typeLine").in(types);
        };

        Specification<Card> totSpec = Specification.where(setSpec).and(colorSpec).and(typeSpec);
        List<Card> res = cardRepository.findAll(totSpec);
        if (res.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No card matching criteria found");
        }
        Random rand = new Random();
        return res.get(rand.nextInt(res.size()));
    }

    public ResponseEntity<byte[]> getRandomCardJpg() {
        Card c = getRandomCard(null, null, null);
        return getCardJpg(c.getId());
    }
    public ResponseEntity<byte[]> getCardJpg(UUID id) {
        Card c = cardRepository.getCardById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card not found"));
        Path jpgPath = Path.of(cardImagesPath, "org", c.getSetCode(), c.getId() + ".jpg");
        if (!Files.exists(jpgPath)) {
            downloadCardImg(c);
        }
        byte[] responseBody;
        try{
            responseBody = Files.readAllBytes(jpgPath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "cardImg not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(responseBody);
    }

    private void downloadCardImg(Card c) {
        byte[] imgBytes = scryfallService.getCardImage(c.getId());
        Path jpgPath = Path.of(cardImagesPath, "org", c.getSetCode(), c.getId() + ".jpg");
        try {
            Files.createDirectories(jpgPath.getParent());
            Files.write(jpgPath, imgBytes);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save card image", new Throwable());
        }
    }

    public ResponseEntity<byte[]> getMaskedCTCCard(UUID id) {
        Card c = cardRepository.getCardById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card not found"));
        Path noctcPath = Path.of(cardImagesPath, "noctc", c.getSetCode(), c.getId() + ".jpg");
        if (!Files.exists(noctcPath)) {
            createNoCtcJpg(c);
        }
        byte[] responseBody;
        try{
            responseBody = Files.readAllBytes(noctcPath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "cardImg not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(responseBody);
    }

    private void createNoCtcJpg(Card c) {
        Path orgPath = Path.of(cardImagesPath, "org", c.getSetCode(), c.getId() + ".jpg");
        Mat orgImg = Imgcodecs.imread(orgPath.toString());
        if (orgImg.empty()) {
            logger.log(Level.SEVERE, "Image not found", new Throwable());
            return;
        }
        Mat orgImgTop = orgImg.submat(0, (int)(orgImg.rows() * 0.2), 0, orgImg.cols());
        Mat grayImgTop = new Mat();
        Imgproc.cvtColor(orgImgTop, grayImgTop, Imgproc.COLOR_BGR2GRAY);
        //HoughCircles
        Mat circles = new Mat();
        Imgproc.HoughCircles(grayImgTop, circles, Imgproc.HOUGH_GRADIENT,
                1.0,
                20.0,
                100.0,
                30.0,
                10,
                40
        );
        if (circles.empty()) {
            logger.log(Level.INFO, "No mana circles found in Img, scryfallID:" + c.getOracleID(), new Throwable());
            return;
        }
        double[] leftMostCircle = null; // center x, center y, radius
        double[] rightMostCircle = null; // center x, center y, radius
        for (int i = 0; i < circles.cols(); i++) {
            double[] curC = circles.get(0,i);
            if (i == 0) {
                leftMostCircle = curC;
                rightMostCircle = curC;
                continue;
            }
            if (curC[0] > rightMostCircle[0]) {
                rightMostCircle = curC;
                leftMostCircle = curC;
            }
        }
        for (int i = 0; i < circles.cols(); i++) {
            double[] curC = circles.get(0,i);
            if (curC[0] < leftMostCircle[0] && Math.abs(curC[1] - leftMostCircle[1]) < 3) {
                leftMostCircle = curC;
            }
        }
        // debug
        Mat result = orgImg.clone();
        for (int i = 0; i < circles.cols(); i++) {
            double[] data = circles.get(0, i);
            Point center = new Point(data[0], data[1]);
            int radius = (int) data[2];

            // draw the circle outline
            Imgproc.circle(result, center, radius, new Scalar(0, 255, 0), 2);
            // draw the center point
            Imgproc.circle(result, center, 2, new Scalar(0, 0, 255), -1);
        }
        Path noCtcPath = Path.of(cardImagesPath, "noctc", c.getSetCode(), c.getId() + ".jpg");
        Imgcodecs.imwrite(noCtcPath.toString(), result);
/*        int extendedRadius = (int)(leftMostCircle[2]*1.1);
        double curXStart = leftMostCircle[0]-extendedRadius*2;
        Rect columnSlice = new Rect((int)curXStart, (int)(leftMostCircle[1]-extendedRadius), extendedRadius, extendedRadius*2);
        double mean = Core.mean(grayImgTop.submat(columnSlice)).val[0]; // mean brightness of a thin area to the left of the leftmost mana circle
        while (curXStart > 0) {
            columnSlice = new Rect((columnSlice.x-extendedRadius), columnSlice.y, columnSlice.width, columnSlice.height);
            if (Math.abs(Core.mean(grayImgTop.submat(columnSlice)).val[0] - mean) > 40) {
                break;
            }
            curXStart-=curXStart-extendedRadius;
        }
        int startXCoord = columnSlice.x + extendedRadius;
        Rect copySlice = new Rect((int)(startXCoord), columnSlice.y, (int)(leftMostCircle[0] - extendedRadius - startXCoord), columnSlice.height); //holding copy area
        Rect StretchSlice = new Rect((int)(startXCoord), columnSlice.y, (int)(rightMostCircle[0] + extendedRadius - startXCoord), columnSlice.height); //holding strech area
        Mat result = orgImg.clone();
        Mat copyArea = orgImg.submat(copySlice);
        Mat pasteArea = result.submat(StretchSlice);
        Imgproc.resize(copyArea, pasteArea, pasteArea.size(), 0, 0, Imgproc.INTER_LINEAR);
        Path noCtcPath = Path.of(cardImagesPath, "noctc", c.getSetCode(), c.getScryfallID() + ".jpg");
        Imgcodecs.imwrite(noCtcPath.toString(), result);*/
    }
}














