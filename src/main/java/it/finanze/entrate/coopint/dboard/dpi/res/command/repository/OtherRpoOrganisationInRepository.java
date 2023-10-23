package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoOrganisationIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoOrganisationInRepositoryRes")
public interface OtherRpoOrganisationInRepository extends JpaRepository<OtherRpoOrganisationIn, Long> {
}