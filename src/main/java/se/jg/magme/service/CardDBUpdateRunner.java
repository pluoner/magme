package se.jg.magme.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import se.jg.magme.config.AppProperties;

@Component
public class CardDBUpdateRunner implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(CardDBUpdateRunner.class.getName());
    private final CardDBUpdateService cardPopulationService;
    private final AppProperties appProperties;

    public CardDBUpdateRunner(CardDBUpdateService cardPopulationService, AppProperties appProperties) {
        this.cardPopulationService = cardPopulationService;
        this.appProperties = appProperties;
    }

    @Override
    public void run(String... args) {
        String startupTarget = appProperties.getStartupTarget();
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