package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonGenerationIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "namePersonGenerationIdentifierRepositoryNR")
public interface NamePersonGenerationIdentifierRepository extends JpaRepository<NamePersonGenerationIdentifier, Long> {
}