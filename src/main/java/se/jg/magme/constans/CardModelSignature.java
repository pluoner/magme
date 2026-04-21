package se.jg.magme.constans;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.stream.Collectors;

import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Id;
import se.jg.magme.model.Card;

public class CardModelSignature {

    private CardModelSignature() {
        throw new UnsupportedOperationException("Static class - do not make an instance of this");
    }
    
    public static String getCardModelSignature() {
        String modelString = Arrays.stream(Card.class.getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(CardModelSignature::describeField)
                .sorted()
                .collect(Collectors.joining("|"));
        return sha256(modelString);
    }

    private static String describeField(Field field) {
        Column column = field.getAnnotation(Column.class);
        Convert convert = field.getAnnotation(Convert.class);

        String columnName = column != null ? column.name() : "";
        String columnDefinition = column != null ? column.columnDefinition() : "";
        String converter = convert != null ? convert.converter().getName() : "";
        String isId = Boolean.toString(field.isAnnotationPresent(Id.class));

        return String.join(":",
                field.getName(),
                field.getType().getName(),
                isId,
                columnName,
                columnDefinition,
                converter);
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
