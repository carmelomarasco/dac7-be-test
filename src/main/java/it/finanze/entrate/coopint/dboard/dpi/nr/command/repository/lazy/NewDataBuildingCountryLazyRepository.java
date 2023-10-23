package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.lazy;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.NewDataBuildingCountryId;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy.NewDataBuildingCountryLazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="newDataBuildingCountryLazyRepositoryNR")
public interface NewDataBuildingCountryLazyRepository extends JpaRepository<NewDataBuildingCountryLazy, NewDataBuildingCountryId> {

	@Query(value = "SELECT DISTINCT * " +
					"FROM NEW_DATA_BUILDING_COUNTRIES BC " +
					"INNER JOIN COUNTRY C ON C.COUNTRY_ID = BC.COUNTRY_ID " +
					"WHERE BC.BUILDING_REPORT_ID = :reportId " +
					"ORDER BY C.DESCRIPTION", nativeQuery = true)
	List<NewDataBuildingCountryLazy> findCountiesConstructionByReportIdOrderByDescription(@Param("reportId")String reportId);

	//List<NewDataBuildingCountryLazy> findQNewDataBuildingCountriesEntitiesByBuildingReportId(String reportId);

}