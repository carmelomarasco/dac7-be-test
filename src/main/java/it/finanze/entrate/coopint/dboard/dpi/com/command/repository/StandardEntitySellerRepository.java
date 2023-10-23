package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySeller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "standardEntitySellerRepositoryNazionale")
public interface StandardEntitySellerRepository extends JpaRepository<StandardEntitySeller, Long> {
}