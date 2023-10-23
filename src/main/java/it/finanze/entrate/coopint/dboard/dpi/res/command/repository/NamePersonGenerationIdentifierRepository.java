package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonGenerationIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "namePersonGenerationIdentifierRepositoryRes")
public interface NamePersonGenerationIdentifierRepository extends JpaRepository<NamePersonGenerationIdentifier, Long> {
}