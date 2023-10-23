package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.Optional;

@Repository (value = "communicationDataRepositoryNazionale")
public interface CommunicationDataRepository extends JpaRepository<CommunicationData, String> {
	
	Optional<CommunicationData> findByComUuid(String comUuid);

	@Query(value = "SELECT COUNT(T1.COM_UUID) FROM COMMUNICATION_DATA T1 " +
			"INNER JOIN COMMUNICATION T2 ON T1.COM_UUID=T2.COM_UUID " +
			"INNER JOIN COMMUNICATION_STATUS T4 ON T2.=T4.STATUS_ID" +
			"WHERE EXTRACT(YEAR FROM T2.REPORTING_PERIOD)=:fy AND T4.STATUS='FINALIZED' " + // AND C.MESSAGE_TYPE_INDIC_ID=:msgTypeIndic
			"AND T1.COMMUNICATION_BLOB IS NULL " +
			"AND T2.DATA=(SELECT MAX(T3.DATA) FROM COMMUNICATION T3 WHERE T3.COM_UUID=T2.COM_UUID) ", nativeQuery = true)
	int existsCommunicationDataFinalizedWithoutContributeForYear(long fy );
	
}