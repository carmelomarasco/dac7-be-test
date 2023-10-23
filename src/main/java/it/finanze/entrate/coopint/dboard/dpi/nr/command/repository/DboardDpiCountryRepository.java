package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.DboardDpiCountry;

@Repository(value = "dboardDpiCountryRepositoryNR")
public interface DboardDpiCountryRepository extends JpaRepository<DboardDpiCountry, Long> {
    // Country che fanno riferimento a countryId per la spedizione
    List<DboardDpiCountry> findByCountryId(long countryId );

    @Query(value = "SELECT * FROM DBOARD_DPI_COUNTRIES F WHERE F.COUNTRY_DESTINATION_ID = F.COUNTRY_ID ORDER BY F.DESCRIPTION", nativeQuery = true)
    List<DboardDpiCountry> findAllEuropean();
    
    Optional<DboardDpiCountry> findByCountryCode(String countryCode);
}