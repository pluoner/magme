package se.jg.magme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jg.magme.model.ScryfallSyncState;

import java.util.Optional;

public interface ScryfallSyncStateRepository extends JpaRepository<ScryfallSyncState, Long> {
    default Optional<ScryfallSyncState> get() {
        return findById(1L);
    }
 }