package se.jg.magme.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "scryfall_id")
    private String scryfallID;
    private String name;
    private String manaCost;
    private int cmc;
    private String typeLine;
    @Column(columnDefinition = "TEXT")
    private String oracleText;
    private String power;
    private String toughness;
    private String colors;
    private String colorIdentity;
    private String setCode;
    private String setName;
    private String rarity;
    @Column(columnDefinition = "TEXT")
    private String flavorText;

}