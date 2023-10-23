package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoRepositoryNR")
public interface OtherRpoRepository extends JpaRepository<OtherRpo, Long> {
}