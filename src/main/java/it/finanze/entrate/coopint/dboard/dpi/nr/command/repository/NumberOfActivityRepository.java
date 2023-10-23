package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NumberOfActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "numberOfActivityRepositoryNR")
public interface NumberOfActivityRepository extends JpaRepository<NumberOfActivity, Long> {
}