package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.RelevantActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "relevantActivityRepositoryRes")
public interface RelevantActivityRepository extends JpaRepository<RelevantActivity, Long> {
}