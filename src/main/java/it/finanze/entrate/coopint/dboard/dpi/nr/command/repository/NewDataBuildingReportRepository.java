package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository(value="newDataBuildingReportRepositoryNR")
public interface NewDataBuildingReportRepository extends JpaRepository<NewDataBuildingReport, String> {

	NewDataBuildingReport findFirstByFiscalYear(Short year);

}