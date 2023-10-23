package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "smRecordErrorRepositoryNazionale")
public interface SmRecordErrorRepository extends JpaRepository<SmRecordError, Long> {
}