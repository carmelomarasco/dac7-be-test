package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MsCountryCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "msCountryCodeRepositoryNazionale")
public interface MsCountryCodeRepository extends JpaRepository<MsCountryCode, Long> {

    MsCountryCode findByValue(String value);

}