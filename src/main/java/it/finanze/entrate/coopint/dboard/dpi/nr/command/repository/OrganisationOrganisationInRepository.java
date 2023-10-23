package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationOrganisationIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "organisationOrganisationInRepositoryNR")
public interface OrganisationOrganisationInRepository extends JpaRepository<OrganisationOrganisationIn, Long> {
}