package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationPlatformBusinessName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "organisationPlatformBusinessNameRepositoryNR")
public interface OrganisationPlatformBusinessNameRepository extends JpaRepository<OrganisationPlatformBusinessName, Long> {
}