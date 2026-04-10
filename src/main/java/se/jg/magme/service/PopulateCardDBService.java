package se.jg.magme.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;


import se.jg.magme.model.Card;
import se.jg.magme.model.ScryfallSyncState;
import se.jg.magme.repository.CardRepository;
import se.jg.magme.repository.ScryfallSyncStateRepository;

@Service
public class PopulateCardDBService {

    private final CardRepository cardRep;
    private final ScryfallService scryfallService;
    private final ScryfallSyncStateRepository scryfallSyncStateRep;
    public PopulateCardDBService(CardRepository cardRep, ScryfallService scryfallService, ScryfallSyncStateRepository scryfallSyncStateRep) {
        this.cardRep = cardRep;
        this.scryfallService = scryfallService;
        this.scryfallSyncStateRep = scryfallSyncStateRep;
    }

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