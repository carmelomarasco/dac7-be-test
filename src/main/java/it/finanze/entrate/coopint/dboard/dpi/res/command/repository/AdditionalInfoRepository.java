package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.AdditionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "additionalInfoRepositoryRes")
public interface AdditionalInfoRepository extends JpaRepository<AdditionalInfo, Long> {
}