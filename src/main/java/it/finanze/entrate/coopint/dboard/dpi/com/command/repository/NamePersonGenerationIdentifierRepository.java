package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonGenerationIdentifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "namePersonGenerationIdentifierRepositoryNazionale")
public interface NamePersonGenerationIdentifierRepository extends JpaRepository<NamePersonGenerationIdentifier, Long> {
}