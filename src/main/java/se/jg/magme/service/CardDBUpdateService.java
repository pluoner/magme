package se.jg.magme.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.jg.magme.model.Card;
import se.jg.magme.model.ScryfallSyncState;
import se.jg.magme.repository.CardRepository;
import se.jg.magme.repository.ScryfallSyncStateRepository;

@Service
@Transactional
public class CardDBUpdateService {

    private static final Logger logger = Logger.getLogger(CardDBUpdateService.class.getName());
    private final CardRepository cardRep;
    private final ScryfallClient scryfallService;
    private final ScryfallSyncStateRepository scryfallSyncStateRep;

    public CardDBUpdateService(CardRepository cardRep, ScryfallClient scryfallService, ScryfallSyncStateRepository scryfallSyncStateRep) {
        this.cardRep = cardRep;
        this.scryfallService = scryfallService;
        this.scryfallSyncStateRep = scryfallSyncStateRep;
    }

    public boolean populateCardDB(PopulateStrategy strategy) {
        ScryfallSyncState scryfallSyncState = scryfallSyncStateRep.get().orElse(new ScryfallSyncState());
        if (strategy == PopulateStrategy.SKIP_IF_EXISTS && scryfallSyncState.getOracleCardsUpdatedAt() != null) {
            logger.log(Level.INFO, "Cards already exist, skipping population");
            return false;
        }
        ScryfallClient.BulkData bulkData = scryfallService.getBulkData();
        if (strategy == PopulateStrategy.CHECK_AND_POPULATE) {
            OffsetDateTime lastUpdated = scryfallSyncState.getOracleUpdatedAt();
            if (lastUpdated != null && !bulkData.updatedAt().isAfter(lastUpdated)) {
                logger.log(Level.INFO, "No updates found, skipping population");
                return false;
            }
        }
        List<Card> cards = scryfallService.getAllOracleCards(bulkData.downloadUri());
        cardRep.deleteAllInBatch();
        cardRep.saveAll(cards);
        scryfallSyncState.setOracleUpdatedAt(bulkData.updatedAt());
        scryfallSyncState.setOracleCardsUpdatedAt(OffsetDateTime.now());
        scryfallSyncStateRep.save(scryfallSyncState);
        return true;
    }
}
