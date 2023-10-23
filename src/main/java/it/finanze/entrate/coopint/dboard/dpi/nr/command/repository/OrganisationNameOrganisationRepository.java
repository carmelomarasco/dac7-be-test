package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationNameOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "organisationNameOrganisationRepositoryNR")
public interface OrganisationNameOrganisationRepository extends JpaRepository<OrganisationNameOrganisation, Long> {
}