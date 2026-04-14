package se.jg.magme.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import se.jg.magme.model.Card;

@Service
public class ScryfallService {

    private final RestClient restClient;
    private final ParameterizedTypeReference<List<Card>> typeRef = new ParameterizedTypeReference<List<Card>>() {
    };

    public ScryfallService(RestClient.Builder builder) {
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        this.restClient = builder
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
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

    public byte[] getCardImage(UUID id) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.scryfall.com")
                        .path("/cards/{id}")
                        .queryParam("format", "image")
                        .queryParam("version", "normal")
                        .build(id))
                .retrieve()
                .body(byte[].class);
    }
    @JsonIgnoreProperties
    public record BulkData(
            @JsonProperty("updated_at") OffsetDateTime updatedAt,
            @JsonProperty("download_uri") URI downloadUri) {
    }

}