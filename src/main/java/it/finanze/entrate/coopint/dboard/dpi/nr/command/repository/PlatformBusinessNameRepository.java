package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PlatformBusinessName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "platformBusinessNameRepositoryNR")
public interface PlatformBusinessNameRepository extends JpaRepository<PlatformBusinessName, Long> {
}