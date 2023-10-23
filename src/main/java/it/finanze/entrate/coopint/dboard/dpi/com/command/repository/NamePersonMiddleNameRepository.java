package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonMiddleName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "namePersonMiddleNameRepositoryNazionale")
public interface NamePersonMiddleNameRepository extends JpaRepository<NamePersonMiddleName, Long> {
}