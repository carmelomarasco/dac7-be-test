package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "organisationInRepositoryNazionale")
public interface OrganisationInRepository extends JpaRepository<OrganisationIn, Long> {
}