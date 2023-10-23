package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.IndividualSeller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "individualSellerRepositoryNazionale")
public interface IndividualSellerRepository extends JpaRepository<IndividualSeller, Long> {
}