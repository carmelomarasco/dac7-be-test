package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PermanentEstablishment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "permanentEstablishmentRepositoryNazionale")
public interface PermanentEstablishmentRepository extends JpaRepository<PermanentEstablishment, Long> {
}