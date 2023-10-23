package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.lazy;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy.NewDataBuildingReportLazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value="newDataBuildingReportLazyRepositoryNR")
public interface NewDataBuildingReportLazyRepository extends JpaRepository<NewDataBuildingReportLazy, String> {
}