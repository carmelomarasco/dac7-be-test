package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardIndividualSeller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "standardIndividualSellerRepositoryNazionale")
public interface StandardIndividualSellerRepository extends JpaRepository<StandardIndividualSeller, Long> {
}