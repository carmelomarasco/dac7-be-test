package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.EntitySeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "entitySellerRepositoryRes")
public interface EntitySellerRepository extends JpaRepository<EntitySeller, Long> {
}