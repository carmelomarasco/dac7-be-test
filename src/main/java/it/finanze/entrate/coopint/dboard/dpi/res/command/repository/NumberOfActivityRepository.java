package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NumberOfActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "numberOfActivityRepositoryRes")
public interface NumberOfActivityRepository extends JpaRepository<NumberOfActivity, Long> {
}