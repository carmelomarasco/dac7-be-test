package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherPlatformOperatorRepositoryNR")
public interface OtherPlatformOperatorRepository extends JpaRepository<OtherPlatformOperator, Long> {
}