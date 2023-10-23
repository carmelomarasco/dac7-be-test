package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="buildingStatusRepositoryNR")
public interface BuildingStatusRepository extends JpaRepository<BuildingStatus, Short> {

	BuildingStatus findByStatusDescription(String statusDescription);

	@Query(value = "SELECT * FROM BUILDING_STATUS S ORDER BY S.STATUS_DESCRIPTION", nativeQuery = true)
	List<BuildingStatus> getAllOrdered();

}