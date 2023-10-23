package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "receiveMessageStatusRepositoryRes")
public interface ReceiveMessageStatusRepository extends JpaRepository<ReceiveMessageStatus, Short> {

	ReceiveMessageStatus findByStatus(String statusTo);

	@Query(value = "SELECT * FROM RECEIVE_MESSAGE_STATUS S ORDER BY S.STATUS_DESCRIPTION", nativeQuery = true)
	List<ReceiveMessageStatus> getAllOrdered();

}