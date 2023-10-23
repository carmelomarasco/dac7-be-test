package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySellerOidFinancialIdentifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "standardEntitySellerOidFinancialIdentifierRepositoryNazionale")
public interface StandardEntitySellerOidFinancialIdentifierRepository extends JpaRepository<StandardEntitySellerOidFinancialIdentifier, Long> {
}