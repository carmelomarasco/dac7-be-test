package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoPlatformBusinessName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "otherRpoPlatformBusinessNameRepositoryNazionale")
public interface OtherRpoPlatformBusinessNameRepository extends JpaRepository<OtherRpoPlatformBusinessName, Long> {
}