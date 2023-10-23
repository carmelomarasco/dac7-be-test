package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PermanentEstablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "permanentEstablishmentRepositoryRes")
public interface PermanentEstablishmentRepository extends JpaRepository<PermanentEstablishment, Long> {
}