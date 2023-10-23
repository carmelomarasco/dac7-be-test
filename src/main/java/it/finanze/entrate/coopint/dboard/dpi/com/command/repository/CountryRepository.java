package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;

@Repository (value = "countryRepositoryNazionale")
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByValue(String value);

    List<Country> findAllByValueIn(Set<String> countryCodes);
    
}