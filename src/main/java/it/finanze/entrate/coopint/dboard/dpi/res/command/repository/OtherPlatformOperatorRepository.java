package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherPlatformOperatorRepositoryRes")
public interface OtherPlatformOperatorRepository extends JpaRepository<OtherPlatformOperator, Long> {
}