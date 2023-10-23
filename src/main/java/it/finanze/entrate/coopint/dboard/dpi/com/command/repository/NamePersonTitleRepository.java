package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonTitle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "namePersonTitleRepositoryNazionale")
public interface NamePersonTitleRepository extends JpaRepository<NamePersonTitle, Long> {
}