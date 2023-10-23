package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonResCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "personResCountryRepositoryNR")
public interface PersonResCountryRepository extends JpaRepository<PersonResCountry, Long> {
}