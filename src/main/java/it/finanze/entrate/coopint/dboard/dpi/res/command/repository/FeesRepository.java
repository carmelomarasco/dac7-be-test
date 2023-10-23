package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "feesRepositoryRes")
public interface FeesRepository extends JpaRepository<Fees, Long> {
}