package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationCountry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "organisationCountryRepositoryNazionale")
public interface OrganisationCountryRepository extends JpaRepository<OrganisationCountry, Long> {
}