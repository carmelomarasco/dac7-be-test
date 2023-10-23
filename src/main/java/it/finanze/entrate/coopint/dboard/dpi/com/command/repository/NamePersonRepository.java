package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "namePersonRepositoryNazionale")
public interface NamePersonRepository extends JpaRepository<NamePerson, Long> {
}