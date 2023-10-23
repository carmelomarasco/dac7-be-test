package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MiddleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "middleNameRepositoryRes")
public interface MiddleNameRepository extends JpaRepository<MiddleName, Long> {
}