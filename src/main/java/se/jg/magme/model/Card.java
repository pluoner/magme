package se.jg.magme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import se.jg.magme.constans.FrameDimensions;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity @Getter @Setter @NoArgsConstructor
public class Card implements Persistable<UUID> {

    @Id
    private UUID id;
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }
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
    @JsonProperty("set")
    private String setCode;
    @JsonProperty("set_name")
    private String setName;
    private String rarity;
    @Column(columnDefinition = "TEXT")
    @JsonProperty("flavor_text")
    private String flavorText;
    private String frame;

    public Path getOrgPath(String cardImagesPath) {
        return Path.of(cardImagesPath, "org", setCode, id + ".jpg");
    } 
    public Path getNoCmcPath(String cardImagesPath) {
        return Path.of(cardImagesPath, "nocmc", setCode, id + ".jpg");
    }

    public int manaRegionStartX() {
        FrameDimensions.FrameDimension fd = FrameDimensions.getFrameDimension(frame);
        int noManaCircles = manaCost.length() - manaCost.replace("{", "").length();
        int manaCirclePixels = noManaCircles * fd.getCmcDiameter();
        int manaCircleSpacePixels = 0;
        if (noManaCircles > 1) {
            manaCircleSpacePixels = (noManaCircles - 1) * fd.getCmcSymbolDistance();
        }
        return fd.getManaBoxRightX() - manaCirclePixels + manaCircleSpacePixels;
    }

    public int manaRegionEndX() {
        FrameDimensions.FrameDimension fd = FrameDimensions.getFrameDimension(frame);
        return fd.getManaBoxRightX();
    }

    public int nameManaRegionTopY() {
        FrameDimensions.FrameDimension fd = FrameDimensions.getFrameDimension(frame);
        return fd.getNameManaRegionTopY();
    }

    public int nameManaRegionBottomY() {
        FrameDimensions.FrameDimension fd = FrameDimensions.getFrameDimension(frame);
        return fd.getNameManaRegionBottomY();
    }

    public int cmcDiameter() {
        FrameDimensions.FrameDimension fd = FrameDimensions.getFrameDimension(frame);
        return fd.getCmcDiameter();
    }

    public int nameManaRegionHeight() {
        FrameDimensions.FrameDimension fd = FrameDimensions.getFrameDimension(frame);
        return fd.nameManaRegionHeight();
    }
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