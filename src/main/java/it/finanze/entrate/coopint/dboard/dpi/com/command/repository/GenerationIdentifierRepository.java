package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.GenerationIdentifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "generationIdentifierRepositoryNazionale")
public interface GenerationIdentifierRepository extends JpaRepository<GenerationIdentifier, Long> {
}