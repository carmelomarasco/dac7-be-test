package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationOrganisationIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "organisationOrganisationInRepositoryNazionale")
public interface OrganisationOrganisationInRepository extends JpaRepository<OrganisationOrganisationIn, Long> {
}