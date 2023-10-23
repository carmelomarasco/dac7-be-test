package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardIndividualSellerFinancialIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "standardIndividualSellerFinancialIdentifierRepositoryRes")
public interface StandardIndividualSellerFinancialIdentifierRepository extends JpaRepository<StandardIndividualSellerFinancialIdentifier, Long> {
}