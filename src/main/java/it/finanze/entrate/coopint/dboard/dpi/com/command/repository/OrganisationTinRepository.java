package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationTin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "organisationTinRepositoryNazionale")
public interface OrganisationTinRepository extends JpaRepository<OrganisationTin, Long> {
}