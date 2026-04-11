package se.jg.magme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity @Getter @Setter @NoArgsConstructor
public class Card {

    @Id
    private UUID id;
    @Column(name = "oracle_id")
    @JsonProperty("oracle_id")
    private String oracleID;
    private String name;
    @JsonProperty("mana_cost")
    private String manaCost;
    private double cmc;
    @JsonProperty("type_line")
    private String typeLine;
    @Column(columnDefinition = "TEXT")
    @JsonProperty("oracle_text")
    private String oracleText;
    private String power;
    private String toughness;
    @Convert(converter = StringListConverter.class)
    private List<String> colors;
    @Convert(converter = StringListConverter.class)
    @JsonProperty("color_identity")
    private List<String> colorIdentity;
    @JsonProperty("set_code")
    private String setCode;
    @JsonProperty("set_name")
    private String setName;
    private String rarity;
    @Column(columnDefinition = "TEXT")
    @JsonProperty("flavor_text")
    private String flavorText;

}

@Converter
class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(",", list);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        if (string == null || string.isEmpty()) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(string.split(",")));
    }
}