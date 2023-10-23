package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.IndividualSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "individualSellerRepositoryRes")
public interface IndividualSellerRepository extends JpaRepository<IndividualSeller, Long> {
}