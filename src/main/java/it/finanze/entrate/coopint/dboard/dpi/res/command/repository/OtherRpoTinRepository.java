package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoTin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoTinRepositoryRes")
public interface OtherRpoTinRepository extends JpaRepository<OtherRpoTin, Long> {
}