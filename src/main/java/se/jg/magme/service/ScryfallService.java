package se.jg.magme.service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import se.jg.magme.model.Card;

@Service
public class ScryfallService {

    private final RestClient restClient;
    private final ParameterizedTypeReference<List<Card>> typeRef = new ParameterizedTypeReference<List<Card>>() {
    };

    public ScryfallService(RestClient.Builder builder) {
        this.restClient = builder
                .defaultHeader("Accept", "application/json")
                .defaultHeader("User-Agent", "Magme/1.0 (contact: jonathan.gerholm@pm.me)")
                .build();
    }

    public BulkData getBulkData() {
        return restClient.get()
                .uri("https://api.scryfall.com/bulk-data/oracle-cards")
                .retrieve()
                .body(BulkData.class);
    }

    public List<Card> getAllOracleCards(URI downloadUri) {
        return restClient.get()
                .uri(downloadUri)
                .retrieve()
                .body(typeRef);
    }
    @JsonIgnoreProperties
    public record BulkData(
            @JsonProperty("updated_at") OffsetDateTime updatedAt,
            @JsonProperty("download_uri") URI downloadUri) {
    }

}