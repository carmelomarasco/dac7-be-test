package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonResCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "personResCountryRepositoryRes")
public interface PersonResCountryRepository extends JpaRepository<PersonResCountry, Long> {
}