package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository(value = "correctableElemStatusRegistryRes")
public interface CorrectableElemStatusRepository extends JpaRepository<CorrectableElemStatus, Short> {

	CorrectableElemStatus findByStatusDescription(String value);

}