package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository(value="correctableElemStatusRegistryNR")
public interface CorrectableElemStatusRepository extends JpaRepository<CorrectableElemStatus, Short> {

	CorrectableElemStatus findByStatusDescription(String description);

}