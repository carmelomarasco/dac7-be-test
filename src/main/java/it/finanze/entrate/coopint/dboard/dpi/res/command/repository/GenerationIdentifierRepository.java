package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.GenerationIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "generationIdentifierRepositoryRes")
public interface GenerationIdentifierRepository extends JpaRepository<GenerationIdentifier, Long> {
}