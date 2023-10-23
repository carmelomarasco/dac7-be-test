package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardIndividualSellerFinancialIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "standardIndividualSellerFinancialIdentifierRepositoryNR")
public interface StandardIndividualSellerFinancialIdentifierRepository extends JpaRepository<StandardIndividualSellerFinancialIdentifier, Long> {
}