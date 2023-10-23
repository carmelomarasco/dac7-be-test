package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperatorsOtherRpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherPlatformOperatorsOtherRpoRepositoryRes")
public interface OtherPlatformOperatorsOtherRpoRepository extends JpaRepository<OtherPlatformOperatorsOtherRpo, Long> {
}