package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Nexus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "nexusRepositoryNazionale")
public interface NexusRepository extends JpaRepository<Nexus, Long> {

    Nexus findByValue(String value);

}