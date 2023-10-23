package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoOrganisationIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoOrganisationInRepositoryNR")
public interface OtherRpoOrganisationInRepository extends JpaRepository<OtherRpoOrganisationIn, Long> {
}