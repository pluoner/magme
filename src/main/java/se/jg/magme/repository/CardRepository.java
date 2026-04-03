package se.jg.magme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import se.jg.magme.model.Card;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    Optional<Card> findByScryfallID(String scryfallID);

    Optional<Card> getCardByScryfallID(String scryfallID);
}