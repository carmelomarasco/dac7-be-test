package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "receiveMessageRepositoryRes")
public interface ReceiveMessageRepository extends JpaRepository<ReceiveMessage, ReceiveMessageId> {

	@Query(value = "SELECT DISTINCT MSG_UUID FROM RECEIVE_MESSAGE M INNER JOIN COUNTRY QC on M.COUNTRY_ID = QC.COUNTRY_ID WHERE QC.VALUE=:cc", nativeQuery = true)
	List<String> getUuidMessageByCountryCode(String cc);

	@Query(value = "SELECT DISTINCT MSG_UUID FROM RECEIVE_MESSAGE M INNER JOIN COUNTRY QC on M.COUNTRY_ID = QC.COUNTRY_ID WHERE QC.VALUE IN :listCC", nativeQuery = true)
	List<String> getUuidMessageByListCountryCode(List<String> listCC);

	@Query(value = "SELECT DISTINCT M.MESSAGE_REF FROM RECEIVE_MESSAGE M WHERE M.MSG_UUID=:msgUuid", nativeQuery = true)
	String getMsgRefByMsgUuid(String msgUuid);

}