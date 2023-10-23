package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySellerOidFinancialIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "standardEntitySellerOidFinancialIdentifierRepositoryRes")
public interface StandardEntitySellerOidFinancialIdentifierRepository extends JpaRepository<StandardEntitySellerOidFinancialIdentifier, Long> {
}