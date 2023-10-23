package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoCountryRepositoryNR")
public interface OtherRpoCountryRepository extends JpaRepository<OtherRpoCountry, Long> {
}