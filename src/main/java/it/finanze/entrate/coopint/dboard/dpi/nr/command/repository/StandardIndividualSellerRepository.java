package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardIndividualSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "standardIndividualSellerRepositoryNR")
public interface StandardIndividualSellerRepository extends JpaRepository<StandardIndividualSeller, Long> {
}