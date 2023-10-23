package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "statusMessageStatusRepositoryRes")
public interface StatusMessageStatusRepository extends JpaRepository<StatusMessageStatus, Short> {

	StatusMessageStatus findByStatus(String value);

	@Query(value = "SELECT * FROM STATUS_MESSAGE_STATUS S ORDER BY S.SM_STATUS_DESCRIPTION", nativeQuery = true)
	List<StatusMessageStatus> getAllOrdered();

	List<StatusMessageStatus> findByStatusIn(List<String> status);

}