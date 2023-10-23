package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PlatformOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "platformOperatorRepositoryRes")
public interface PlatformOperatorRepository extends JpaRepository<PlatformOperator, Long> {
}