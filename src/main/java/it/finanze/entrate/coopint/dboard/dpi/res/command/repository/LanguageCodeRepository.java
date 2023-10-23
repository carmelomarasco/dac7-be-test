package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "languageCodeRepositoryRes")
public interface LanguageCodeRepository extends JpaRepository<LanguageCode, Long> {
}