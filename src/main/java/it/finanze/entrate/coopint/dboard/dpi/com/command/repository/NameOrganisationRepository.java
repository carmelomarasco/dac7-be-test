package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NameOrganisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "nameOrganisationRepositoryNazionale")
public interface NameOrganisationRepository extends JpaRepository<NameOrganisation, Long> {
}