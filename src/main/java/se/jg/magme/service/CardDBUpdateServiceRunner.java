package se.jg.magme.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class CardDBUpdateServiceRunner implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(CardDBUpdateServiceRunner.class.getName());
    private final CardDBUpdateService cardPopulationService;
    @Value("${app.startup-target}")
    private String startupTarget;

    public CardDBUpdateServiceRunner(CardDBUpdateService cardPopulationService) {
        this.cardPopulationService = cardPopulationService;
    }

    @Override
    public void run(String... args) {
        if (args.length != 0) {
            startupTarget = args[0];
        }
        try {
            PopulateStrategy popStrat = PopulateStrategy.valueOf(startupTarget);
            cardPopulationService.populateCardDB(popStrat);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Invalid argument to run PopulateCardDBService", new Throwable());
        }
    }
}

enum PopulateStrategy {
    FORCE, CHECK_AND_POPULATE, SKIP_IF_EXISTS
}