package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "otherActivityRepositoryNazionale")
public interface OtherActivityRepository extends JpaRepository<OtherActivity, Long> {
}