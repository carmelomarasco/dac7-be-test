package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "organisationAddressRepositoryNR")
public interface OrganisationAddressRepository extends JpaRepository<OrganisationAddress, Long> {
}