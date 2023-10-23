package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoNameOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoNameOrganisationRepositoryRes")
public interface OtherRpoNameOrganisationRepository extends JpaRepository<OtherRpoNameOrganisation, Long> {
}