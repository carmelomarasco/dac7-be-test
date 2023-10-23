package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherActivityRepositoryNR")
public interface OtherActivityRepository extends JpaRepository<OtherActivity, Long> {
}