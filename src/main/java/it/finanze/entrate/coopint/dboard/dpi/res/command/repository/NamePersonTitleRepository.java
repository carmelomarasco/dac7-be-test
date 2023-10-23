package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "namePersonTitleRepositoryRes")
public interface NamePersonTitleRepository extends JpaRepository<NamePersonTitle, Long> {
}