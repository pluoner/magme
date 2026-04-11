package se.jg.magme.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


import se.jg.magme.model.Card;
import se.jg.magme.model.ScryfallSyncState;
import se.jg.magme.repository.CardRepository;
import se.jg.magme.repository.ScryfallSyncStateRepository;

@Service
public class PopulateCardDBService implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(PopulateCardDBService.class.getName());
    private final CardRepository cardRep;
    private final ScryfallService scryfallService;
    private final ScryfallSyncStateRepository scryfallSyncStateRep;
    @Value("${app.startup-target}")
    private String startupTarget;

    @Override
    public void run(String... args) {
        if (args.length != 0) {
            startupTarget = args[0];
        }
        try {
            PopulateStrategy popStrat = PopulateStrategy.valueOf(startupTarget);
            populateCardDB(popStrat);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Invalid argument to run PopulateCardDBService", new Throwable());
        }
    }

    public PopulateCardDBService(CardRepository cardRep, ScryfallService scryfallService, ScryfallSyncStateRepository scryfallSyncStateRep) {
        this.cardRep = cardRep;
        this.scryfallService = scryfallService;
        this.scryfallSyncStateRep = scryfallSyncStateRep;
    }

    //Todo: Better logging here, INFO on what has is done, logg if something fails etc.
    public boolean populateCardDB(PopulateStrategy strategy) {
        ScryfallSyncState scryfallSyncState = scryfallSyncStateRep.get().orElse(new ScryfallSyncState());
        if (strategy == PopulateStrategy.SKIP_IF_EXISTS && scryfallSyncState.getOracleCardsUpdatedAt() != null) {
            return false;
        }
        ScryfallService.BulkData bulkData = scryfallService.getBulkData();
        if (strategy == PopulateStrategy.CHECK_AND_POPULATE) {
            OffsetDateTime lastUpdated = scryfallSyncState.getOracleUpdatedAt();
            if (lastUpdated != null && !bulkData.updatedAt().isAfter(lastUpdated)) {
                return false;
            }
        }
        List<Card> cards = scryfallService.getAllOracleCards(bulkData.downloadUri());
        //Todo: saveAll(cards) takes about 2 min, seems to do single inserts. Ok for now, but should be adressed at some point.
        //Todo: Both saves should be "one transaction", if saveAll failes the syncState should not be updated.
        cardRep.saveAll(cards);
        scryfallSyncState.setOracleUpdatedAt(bulkData.updatedAt());
        scryfallSyncState.setOracleCardsUpdatedAt(OffsetDateTime.now());
        scryfallSyncStateRep.save(scryfallSyncState);
        return true;
    }
}

enum PopulateStrategy {
    FORCE, CHECK_AND_POPULATE, SKIP_IF_EXISTS
}