package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "receiveMessageTupeIndicRepositoryRes")
public interface ReceiveMessageTypeIndicRepository extends JpaRepository<ReceiveMessageTypeIndic, Short> {

	ReceiveMessageTypeIndic findByMtiDescription(String mtiDescription);

	@Query(value = "SELECT * FROM RECEIVE_MESSAGE_TYPE_INDIC T ORDER BY T.MTI_DESCRIPTION", nativeQuery = true)
	List<ReceiveMessageTypeIndic> getAllOrdered();

}