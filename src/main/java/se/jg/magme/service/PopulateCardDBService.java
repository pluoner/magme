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
        BulkData bulkData = scryfallService.getBulkData();
        URI downloadUri = parseDownloadUri(bulkDataJson);
        List<Card> cards = scryfallService.getAllOracleCards(downloadUri);
        cardRepository.saveAll(cards);
    }
    private URI parseDownloadUri(String bulkDataJson) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseDownloadUri'");
    }

    public BulkData getBulkData() {
        return scryfallService.getBulkData();
    }

    @JsonIgnoreProperties
    record BulkData(
        @JsonProperty("updated_at") OffsetDateTime updatedAt,
        @JsonProperty("download_uri") URI downloadUri) {
    }
}