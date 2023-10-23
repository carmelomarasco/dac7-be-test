package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PlatformBusinessName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "platformBusinessNameRepositoryRes")
public interface PlatformBusinessNameRepository extends JpaRepository<PlatformBusinessName, Long> {
}