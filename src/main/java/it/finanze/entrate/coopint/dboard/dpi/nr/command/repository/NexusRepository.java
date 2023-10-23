package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Nexus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "nexusRepositoryNR")
public interface NexusRepository extends JpaRepository<Nexus, Long> {

    Nexus findByValue(String value);

}