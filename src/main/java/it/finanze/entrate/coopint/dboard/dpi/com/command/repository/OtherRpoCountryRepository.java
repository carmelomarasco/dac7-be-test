package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoCountry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "otherRpoCountryRepositoryNazionale")
public interface OtherRpoCountryRepository extends JpaRepository<OtherRpoCountry, Long> {
}