package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.EntitySeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "entitySellerRepositoryNR")
public interface EntitySellerRepository extends JpaRepository<EntitySeller, Long> {
}