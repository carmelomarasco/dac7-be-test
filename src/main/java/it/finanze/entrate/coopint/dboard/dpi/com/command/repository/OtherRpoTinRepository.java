package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoTin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "otherRpoTinRepositoryNazionale")
public interface OtherRpoTinRepository extends JpaRepository<OtherRpoTin, Long> {
}