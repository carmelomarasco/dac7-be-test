package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationNameOrganisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "organisationNameOrganisationRepositoryNazionale")
public interface OrganisationNameOrganisationRepository extends JpaRepository<OrganisationNameOrganisation, Long> {
}