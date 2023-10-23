package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.FinancialIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "financialIdentifierRepositoryRes")
public interface FinancialIdentifierRepository extends JpaRepository<FinancialIdentifier, Long> {
}