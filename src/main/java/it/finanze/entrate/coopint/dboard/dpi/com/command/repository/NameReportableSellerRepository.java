package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NameReportableSeller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "nameReportableSellerRepositoryNazionale")
public interface NameReportableSellerRepository extends JpaRepository<NameReportableSeller, Long> {
}