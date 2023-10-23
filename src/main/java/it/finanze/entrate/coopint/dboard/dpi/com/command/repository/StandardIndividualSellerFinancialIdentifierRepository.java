package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardIndividualSellerFinancialIdentifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "standardIndividualSellerFinancialIdentifierRepositoryNazionale")
public interface StandardIndividualSellerFinancialIdentifierRepository extends JpaRepository<StandardIndividualSellerFinancialIdentifier, Long> {
}