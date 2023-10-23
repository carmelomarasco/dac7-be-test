package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Amount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "amountRepositoryNazionale")
public interface AmountRepository extends JpaRepository<Amount, Long> {
}