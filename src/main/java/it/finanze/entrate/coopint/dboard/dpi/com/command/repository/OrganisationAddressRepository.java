package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "organisationAddressRepositoryNazionale")
public interface OrganisationAddressRepository extends JpaRepository<OrganisationAddress, Long> {
}