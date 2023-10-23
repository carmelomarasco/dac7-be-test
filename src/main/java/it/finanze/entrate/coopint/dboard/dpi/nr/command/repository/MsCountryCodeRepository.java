package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MsCountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "msCountryCodeRepositoryNR")
public interface MsCountryCodeRepository extends JpaRepository<MsCountryCode, Long> {

    MsCountryCode findByValue(String value);

}