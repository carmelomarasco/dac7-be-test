package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonNamePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "personNamePersonRepositoryNR")
public interface PersonNamePersonRepository extends JpaRepository<PersonNamePerson, Long> {
}