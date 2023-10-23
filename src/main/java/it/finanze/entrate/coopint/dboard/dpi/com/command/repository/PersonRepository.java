package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Person;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "personRepositoryNazionale")
public interface PersonRepository extends JpaRepository<Person, Long> {
}