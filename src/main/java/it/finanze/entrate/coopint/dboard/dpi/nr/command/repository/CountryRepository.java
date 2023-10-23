package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository (value = "countryRepositoryNR")
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByValue(String value);

    List<Country> findAllByValueIn(Set<String> countryCodes);

    @Query(value = "SELECT DISTINCT * FROM COUNTRY C ORDER BY C.DESCRIPTION", nativeQuery = true)
    List<Country> getAllCountryOrderByDesc();

}