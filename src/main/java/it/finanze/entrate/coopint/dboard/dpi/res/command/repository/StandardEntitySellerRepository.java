package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "standardEntitySellerRepositoryRes")
public interface StandardEntitySellerRepository extends JpaRepository<StandardEntitySeller, Long> {
}