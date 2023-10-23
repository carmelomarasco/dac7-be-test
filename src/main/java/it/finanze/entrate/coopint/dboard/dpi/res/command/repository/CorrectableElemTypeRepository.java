package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository(value = "correctableElemTypeRepositoryRes")
public interface CorrectableElemTypeRepository extends JpaRepository<CorrectableElemType, Short> {

	CorrectableElemType findByTypeDescription(String value);

}