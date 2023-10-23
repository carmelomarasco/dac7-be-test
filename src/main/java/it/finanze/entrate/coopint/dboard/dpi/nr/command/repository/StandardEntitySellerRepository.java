package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "standardEntitySellerRepositoryNR")
public interface StandardEntitySellerRepository extends JpaRepository<StandardEntitySeller, Long> {
}