package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonNationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "personNationalityRepositoryNR")
public interface PersonNationalityRepository extends JpaRepository<PersonNationality, Long> {
}