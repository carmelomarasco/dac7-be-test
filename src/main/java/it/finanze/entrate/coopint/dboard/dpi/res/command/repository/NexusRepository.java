package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Nexus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "nexusRepositoryRes")
public interface NexusRepository extends JpaRepository<Nexus, Long> {

    Nexus findByValue(String value);

}