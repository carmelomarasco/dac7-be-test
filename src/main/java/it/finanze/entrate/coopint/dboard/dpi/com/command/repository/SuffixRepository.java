package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Suffix;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "suffixRepositoryNazionale")
public interface SuffixRepository extends JpaRepository<Suffix, Long> {
}