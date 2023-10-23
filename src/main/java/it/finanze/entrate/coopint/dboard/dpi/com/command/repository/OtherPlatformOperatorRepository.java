package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "otherPlatformOperatorRepositoryNazionale")
public interface OtherPlatformOperatorRepository extends JpaRepository<OtherPlatformOperator, Long> {
}