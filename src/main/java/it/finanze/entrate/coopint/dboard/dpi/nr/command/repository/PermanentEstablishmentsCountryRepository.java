package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PermanentEstablishmentsCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "permanentEstablishmentsCountryRepositoryNR")
public interface PermanentEstablishmentsCountryRepository extends JpaRepository<PermanentEstablishmentsCountry, Long> {
}