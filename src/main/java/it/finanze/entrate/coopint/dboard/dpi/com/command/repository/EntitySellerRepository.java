package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.EntitySeller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "entitySellerRepositoryNazionale")
public interface EntitySellerRepository extends JpaRepository<EntitySeller, Long> {
}