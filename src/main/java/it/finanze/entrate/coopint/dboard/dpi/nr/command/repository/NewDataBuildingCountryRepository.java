package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="newDataBuildingCountryRepositoryNR")
public interface NewDataBuildingCountryRepository extends JpaRepository<NewDataBuildingCountry, NewDataBuildingCountryId> {

	@Query(value = "SELECT DISTINCT * " +
					"FROM NEW_DATA_BUILDING_COUNTRIES BC " +
					"INNER JOIN COUNTRY C ON C.COUNTRY_ID = BC.COUNTRY_ID " +
					"WHERE BC.BUILDING_REPORT_ID = :reportId " +
					"ORDER BY C.DESCRIPTION", nativeQuery = true)
	List<NewDataBuildingCountry> findCountiesConstructionByReportIdOrderByDescription(@Param("reportId")String reportId);

	//List<NewDataBuildingCountry> findQNewDataBuildingCountriesEntitiesByBuildingReportId(String reportId);

}