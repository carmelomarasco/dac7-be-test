package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.AdditionalInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "additionalInfoRepositoryNazionale")
public interface AdditionalInfoRepository extends JpaRepository<AdditionalInfo, Long> {
}