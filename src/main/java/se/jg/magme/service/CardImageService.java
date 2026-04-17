package se.jg.magme.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import se.jg.magme.model.Card;
import se.jg.magme.repository.CardRepository;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CardImageService {

    @Value("${app.card-images-path}")
    private String cardImagesPath;

    private final CardRepository cardRepository;
    private final ScryfallClient scryfallService;
    private final CardService cardService;
    private static final Logger logger = Logger.getLogger(CardImageService.class.getName());
    public CardImageService(CardRepository cardRepository, ScryfallClient scryfallService, CardService cardService) {
        this.cardRepository = cardRepository;
        this.scryfallService = scryfallService;
        this.cardService = cardService;
    }

    public ResponseEntity<byte[]> getCard(UUID id) {
        Card c = cardRepository.getCardById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card record not found"));
        if (!Files.exists(c.getOrgPath(cardImagesPath))) {
            fetchOrg(c);
        }
        byte[] responseBody;
        try{
            responseBody = Files.readAllBytes(c.getOrgPath(cardImagesPath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Original card image not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(responseBody);
    }

    private void fetchOrg(Card c) {
        byte[] imgBytes = scryfallService.getCardImage(c.getId());
        try {
            Files.createDirectories(c.getOrgPath(cardImagesPath).getParent());
            Files.write(c.getOrgPath(cardImagesPath), imgBytes);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save original card image", new Throwable());
        }
    }

    public ResponseEntity<byte[]> getNoCmc(UUID id) {
        Card c;
        if (id == null) {
            c = cardService.getRandomCard(null, null, null);
        } else {
            c = cardRepository.getCardById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Card not found"));
        }
        while (true) {
            createNoCmc(c);
            if (Files.exists(c.getNoCmcPath(cardImagesPath))) {
                break;
            }
            c = cardService.getRandomCard(null, null, null);
        }
        byte[] responseBody;
        try{
            responseBody = Files.readAllBytes(c.getNoCmcPath(cardImagesPath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "cardImg not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(responseBody);
    }

    private void createNoCmc(Card c) {
        Mat orgImg;
        if (!Files.exists(c.getOrgPath(cardImagesPath))) {
            fetchOrg(c);
        }
        try {
            orgImg = readImage(c.getOrgPath(cardImagesPath));
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
        //HoughCircles
        Mat circles = new Mat();
        Imgproc.equalizeHist(grayImgTop, grayImgTop);
        Imgproc.HoughCircles(grayImgTop, circles, Imgproc.HOUGH_GRADIENT_ALT,
                1.0,
                5.0,
                100.0,
                0.8,
                5,
                20
        );
        if (circles.empty() && c.getManaCost() != null && !c.getManaCost().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No mana circles found in Img, scryfallID: " + c.getOracleID() + ", mana cost is: " + c.getManaCost());
        } else if (circles.empty()) {
            logger.log(Level.INFO, "No mana circles found in Img, scryfallID: " + c.getOracleID() + ", mana cost is empty", new Throwable());
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
        Path noCmcPath = Path.of(cardImagesPath, "nocmc", c.getSetCode(), c.getId() + ".jpg");
        try {
            Files.createDirectories(noCmcPath.getParent());
            Imgcodecs.imwrite(noCmcPath.toString(), result);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save nocmc card image", new Throwable());
        }
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
        Path noCmcPath = Path.of(cardImagesPath, "nocmc", c.getSetCode(), c.getScryfallID() + ".jpg");
        Imgcodecs.imwrite(noCmcPath.toString(), result);*/
    }

    private Mat readImage(Path path) throws IOException {
        BufferedImage img = ImageIO.read(path.toFile());
        Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, pixels);
        return mat;
    }
}














