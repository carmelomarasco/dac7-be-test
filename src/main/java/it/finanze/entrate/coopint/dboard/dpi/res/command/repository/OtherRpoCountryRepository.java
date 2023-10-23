package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoCountryRepositoryRes")
public interface OtherRpoCountryRepository extends JpaRepository<OtherRpoCountry, Long> {
}