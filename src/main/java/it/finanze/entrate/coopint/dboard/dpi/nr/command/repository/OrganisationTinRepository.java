package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationTin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "organisationTinRepositoryNR")
public interface OrganisationTinRepository extends JpaRepository<OrganisationTin, Long> {
}