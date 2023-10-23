package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Title;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "titleRepositoryNazionale")
public interface TitleRepository extends JpaRepository<Title, Long> {
}