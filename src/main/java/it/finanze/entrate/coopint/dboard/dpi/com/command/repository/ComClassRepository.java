package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.ComClassEntity;

@Repository(value = "comClassRepositoryNazionale")
public interface ComClassRepository extends JpaRepository<ComClassEntity, Long> {

    ComClassEntity findByComClassDescription(String comClassDescription);
}
