package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.RelevantActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "relevantActivityRepositoryNazionale")
public interface RelevantActivityRepository extends JpaRepository<RelevantActivity, Long> {
}