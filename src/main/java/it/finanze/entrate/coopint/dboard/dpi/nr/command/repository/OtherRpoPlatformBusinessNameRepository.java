package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoPlatformBusinessName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoPlatformBusinessNameRepositoryNR")
public interface OtherRpoPlatformBusinessNameRepository extends JpaRepository<OtherRpoPlatformBusinessName, Long> {
}