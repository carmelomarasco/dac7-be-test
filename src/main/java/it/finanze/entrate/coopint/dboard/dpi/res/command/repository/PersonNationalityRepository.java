package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonNationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "personNationalityRepositoryRes")
public interface PersonNationalityRepository extends JpaRepository<PersonNationality, Long> {
}