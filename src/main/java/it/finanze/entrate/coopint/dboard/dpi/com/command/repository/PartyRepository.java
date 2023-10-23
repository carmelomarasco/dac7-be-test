package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Party;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "partyRepositoryNazionale")
public interface PartyRepository extends JpaRepository<Party, Long> {
}