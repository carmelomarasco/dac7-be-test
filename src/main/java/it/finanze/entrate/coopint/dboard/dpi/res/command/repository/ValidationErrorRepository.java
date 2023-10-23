package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository(value = "validationErrorRepositoryRes")
public interface ValidationErrorRepository extends JpaRepository<ValidationError, String> {

	ValidationError findQValidationErrorsByValidationErrorUuid(String errUuid);

}