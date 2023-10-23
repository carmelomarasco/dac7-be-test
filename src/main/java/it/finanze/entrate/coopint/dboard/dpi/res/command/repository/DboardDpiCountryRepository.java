package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;


import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.DboardDpiCountry;

import java.util.List;
import java.util.Optional;

@Repository(value = "dboardDpiCountryRepositoryRes")
public interface DboardDpiCountryRepository extends JpaRepository<DboardDpiCountry, Long> {
    // Country che fanno riferimento a countryId per la spedizione
    List<DboardDpiCountry> findByCountryId(long countryId );

    Optional<DboardDpiCountry> findByCountryCode(String countryCode);

    @Query(value = "SELECT DISTINCT * FROM DBOARD_DPI_COUNTRIES C WHERE COUNTRY_DESTINATION_ID = COUNTRY_ID  ORDER BY C.DESCRIPTION" , nativeQuery = true)
    List<DboardDpiCountry> getAllOrderByDesc();
}