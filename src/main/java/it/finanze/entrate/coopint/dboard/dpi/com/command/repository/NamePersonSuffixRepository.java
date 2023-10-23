package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonSuffix;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "namePersonSuffixRepositoryNazionale")
public interface NamePersonSuffixRepository extends JpaRepository<NamePersonSuffix, Long> {
}