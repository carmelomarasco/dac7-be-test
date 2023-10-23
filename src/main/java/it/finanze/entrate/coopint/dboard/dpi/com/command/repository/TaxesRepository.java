package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Taxes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "taxesRepositoryNazionale")
public interface TaxesRepository extends JpaRepository<Taxes, Long> {
}