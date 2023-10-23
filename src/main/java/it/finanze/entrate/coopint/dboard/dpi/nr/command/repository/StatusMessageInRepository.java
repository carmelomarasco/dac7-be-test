package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value="statusMessageInRepositoryNR")
public interface StatusMessageInRepository extends JpaRepository<StatusMessageIn, String> {

	@Query(value = "SELECT * FROM (SELECT * FROM STATUS_MESSAGE_IN SM WHERE SM.MSG_UUID =:msgUuid ORDER BY SM.STATUS_MESSAGE_UUID DESC) WHERE ROWNUM < 2", nativeQuery = true)
	StatusMessageIn findFirstByMsgUuid(@Param("msgUuid")String msgUuid);

	@Query(value = "SELECT SM.VALIDATION_RESULT FROM STATUS_MESSAGE_IN SM WHERE SM.MSG_UUID =:msgUuid", nativeQuery = true)
	List<String> getValidationResultByMsgUuid(@Param("msgUuid")String msgUuid);

	Optional<StatusMessageIn> findFirstStatusMessageInByMsgUuidOrderByDataInsDesc(String msgUuid);

}