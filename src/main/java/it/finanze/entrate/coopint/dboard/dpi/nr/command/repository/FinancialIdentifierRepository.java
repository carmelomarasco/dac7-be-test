package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.FinancialIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "financialIdentifierRepositoryNR")
public interface FinancialIdentifierRepository extends JpaRepository<FinancialIdentifier, Long> {
}