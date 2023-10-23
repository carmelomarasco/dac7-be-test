package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="msgStatusRepositoryNR")
public interface MsgStatusRepository extends JpaRepository<MsgStatus, Short> {

	MsgStatus findByStatus(String status);

	@Query(value = "SELECT * FROM MSG_STATUS S ORDER BY S.STATUS_DESCRIPTION", nativeQuery = true)
	List<MsgStatus> getAllOrdered();

	List<MsgStatus> findByStatusIn(List<String> statusDescription);

}