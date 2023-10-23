package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.ReportableSeller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "reportableSellerRepositoryNazionale")
public interface ReportableSellerRepository extends JpaRepository<ReportableSeller, Long> {
}