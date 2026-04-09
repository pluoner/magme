package se.jg.magme.service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import se.jg.magme.model.Card;
import se.jg.magme.repository.CardRepository;
import se.jg.magme.repository.ScryfallSyncStateRepository;

@Service
public class PopulateCardDBService {

    private final CardRepository cardRepository;
    private final ScryfallService scryfallService;
    private final ScryfallSyncStateRepository scryfallSyncStateRepository;
    public PopulateCardDBService(CardRepository cardRepository, ScryfallService scryfallService, ScryfallSyncStateRepository scryfallSyncStateRepository) {
        this.cardRepository = cardRepository;
        this.scryfallService = scryfallService;
        this.scryfallSyncStateRepository = scryfallSyncStateRepository;
    }

    public boolean populateCardDB(boolean force) {
        ScryfallService.BulkData bulkData = scryfallService.getBulkData();
        List<Card> cards = scryfallService.getAllOracleCards(bulkData.downloadUri());
        cardRepository.saveAll(cards);
        return true;
    }
    private URI parseDownloadUri(String bulkDataJson) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseDownloadUri'");
    }
}