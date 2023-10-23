package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "organisationCountryRepositoryRes")
public interface OrganisationCountryRepository extends JpaRepository<OrganisationCountry, Long> {
}