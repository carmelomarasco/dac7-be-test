package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NameOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "nameOrganisationRepositoryRes")
public interface NameOrganisationRepository extends JpaRepository<NameOrganisation, Long> {
}