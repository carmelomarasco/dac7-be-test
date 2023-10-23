package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "smFileAcceptanceStatusRepositoryNazionale")
public interface SmFileAcceptanceStatusRepository extends JpaRepository<SmFileAcceptanceStatus, Long> {
}