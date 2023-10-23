package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonSuffix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "namePersonSuffixRepositoryNR")
public interface NamePersonSuffixRepository extends JpaRepository<NamePersonSuffix, Long> {
}