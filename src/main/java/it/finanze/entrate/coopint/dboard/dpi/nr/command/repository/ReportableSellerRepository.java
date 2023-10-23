package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.ReportableSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "reportableSellerRepositoryNR")
public interface ReportableSellerRepository extends JpaRepository<ReportableSeller, Long> {
}