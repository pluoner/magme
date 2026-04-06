package se.jg.magme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MagmeApplication {

    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        SpringApplication.run(MagmeApplication.class, args);
    }

}
