package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Tin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "tinRepositoryNazionale")
public interface TinRepository extends JpaRepository<Tin, Long> {
}