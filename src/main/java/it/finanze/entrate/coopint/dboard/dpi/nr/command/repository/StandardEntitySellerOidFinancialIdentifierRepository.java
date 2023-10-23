package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySellerOidFinancialIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "standardEntitySellerOidFinancialIdentifierRepositoryNR")
public interface StandardEntitySellerOidFinancialIdentifierRepository extends JpaRepository<StandardEntitySellerOidFinancialIdentifier, Long> {
}