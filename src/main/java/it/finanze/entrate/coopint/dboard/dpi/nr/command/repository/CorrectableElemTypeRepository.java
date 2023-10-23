package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository(value="correctableElemTypeRepositoryNR")
public interface CorrectableElemTypeRepository extends JpaRepository<CorrectableElemType, Short> {

	CorrectableElemType findByTypeDescription(String description);

}