package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "statusMessageRepositoryRes")
public interface StatusMessageRepository extends JpaRepository<StatusMessage, StatusMessageId> {

	@Query(value = "SELECT SM.MSG_UUID " +
					"FROM STATUS_MESSAGE SM " +
					"WHERE SM.SM_STATUS_ID IN :listStatusId " +
					"AND SM_CHANGE_DATE = (SELECT MAX(SM2.SM_CHANGE_DATE) " +
					"FROM STATUS_MESSAGE SM2 " +
					"WHERE SM.MSG_UUID = SM2.MSG_UUID)", nativeQuery = true)
	List<String> getMsgUuidByListIdStatusSMLast(List<String> listStatusId);

	@Query(value = "SELECT SM.MSG_UUID FROM STATUS_MESSAGE SM WHERE SM.VALIDATION_RESULT IN :listOutcome", nativeQuery = true)
	List<String> getMsgUuidByListOutcome(List<String> listOutcome);

	@Query(value = "SELECT SM.VALIDATION_RESULT FROM STATUS_MESSAGE SM WHERE SM.MSG_UUID =:msgUuid", nativeQuery = true)
	List<String> getListOutcomeMsg(String msgUuid);

	Optional<StatusMessage> findById_smUuid(String smUuid);

	List<StatusMessage> findStatusMessagesByMsgUuidOrderById_smChangeDate(String msgUuid);

	StatusMessage findStatusMessageById_smUuid(String smUuid);

	@Query(value = "SELECT * " +
					"FROM STATUS_MESSAGE SM " +
					"WHERE SM.MSG_UUID = :msgUuid " +
					"AND SM.SM_STATUS_ID IN (SELECT S.SM_STATUS_ID " +
					"FROM STATUS_MESSAGE_STATUS S " +
					"WHERE S.STATUS IN :listStatus)", nativeQuery = true)
	List<StatusMessage> getListSmByMsgUuidAndListStatus(String msgUuid, List<String> listStatus);

}