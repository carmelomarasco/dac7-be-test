package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonMiddleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "namePersonMiddleNameRepositoryRes")
public interface NamePersonMiddleNameRepository extends JpaRepository<NamePersonMiddleName, Long> {
}