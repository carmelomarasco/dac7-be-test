package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PlatformBusinessName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "platformBusinessNameRepositoryNazionale")
public interface PlatformBusinessNameRepository extends JpaRepository<PlatformBusinessName, Long> {
}