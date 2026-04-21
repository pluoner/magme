package se.jg.magme.model;

import java.time.OffsetDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class ScryfallSyncState {

    @Id
    private Long id = 1L;
    private OffsetDateTime oracleUpdatedAt;
    private OffsetDateTime oracleCardsUpdatedAt;
    private String cardModelSignature;
}