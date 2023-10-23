package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperatorsOtherRpo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "otherPlatformOperatorsOtherRpoRepositoryNazionale")
public interface OtherPlatformOperatorsOtherRpoRepository extends JpaRepository<OtherPlatformOperatorsOtherRpo, Long> {
}