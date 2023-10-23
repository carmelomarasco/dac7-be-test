package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationPlatformBusinessName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "organisationPlatformBusinessNameRepositoryNazionale")
public interface OrganisationPlatformBusinessNameRepository extends JpaRepository<OrganisationPlatformBusinessName, Long> {
}