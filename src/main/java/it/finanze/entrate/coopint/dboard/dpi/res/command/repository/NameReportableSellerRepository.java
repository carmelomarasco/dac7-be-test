package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NameReportableSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "nameReportableSellerRepositoryRes")
public interface NameReportableSellerRepository extends JpaRepository<NameReportableSeller, Long> {
}