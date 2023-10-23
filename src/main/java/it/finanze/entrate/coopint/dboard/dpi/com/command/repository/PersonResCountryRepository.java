package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonResCountry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "personResCountryRepositoryNazionale")
public interface PersonResCountryRepository extends JpaRepository<PersonResCountry, Long> {
}