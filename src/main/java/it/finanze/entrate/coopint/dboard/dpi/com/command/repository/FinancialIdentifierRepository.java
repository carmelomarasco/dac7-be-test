package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.FinancialIdentifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "financialIdentifierRepositoryNazionale")
public interface FinancialIdentifierRepository extends JpaRepository<FinancialIdentifier, Long> {
}