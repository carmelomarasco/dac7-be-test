package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "dboardDpiCountryRepositoryNazionale")
public interface DboardDpiCountryRepository extends JpaRepository<DboardDpiCountry, Long> {
    // Country che fanno riferimento a countryId per la spedizione
    List<DboardDpiCountry> findByCountryId(long countryId );

    Optional<DboardDpiCountry> findByCountryCode(String countryCode);
}