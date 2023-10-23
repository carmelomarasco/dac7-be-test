package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Taxes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "taxesRepositoryRes")
public interface TaxesRepository extends JpaRepository<Taxes, Long> {
}