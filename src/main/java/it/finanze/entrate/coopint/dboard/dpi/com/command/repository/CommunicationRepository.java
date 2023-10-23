package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;

@Repository (value = "communicationRepositoryNazionale")
public interface CommunicationRepository extends JpaRepository<Communication, String> {
	
    boolean existsByProtocol(String protocol);

    @Query(value = "SELECT * " +
            "FROM COMMUNICATION C " +
            "WHERE C.PROTOCOL =:protocol " +
            "AND DATA=(SELECT MAX(C1.DATA) FROM COMMUNICATION C1 WHERE C1.COM_UUID=C.COM_UUID) " +
            "AND ROWNUM<2", nativeQuery = true)
    Communication findByProtocol(String protocol);

    Optional<List<Communication>> findAllByProtocolIn(List<String> protocols);

    @Query(value = "SELECT * FROM COMMUNICATION C " +
            "WHERE YEAR(C.REPORTING_PERIOD)=:fy AND C.MESSAGE_TYPE_INDIC_ID=:msgTypeIndic AND C.STATUS_ID=:statusId " +
            "AND DATA=(SELECT MAX(C1.DATA) FROM COMMUNICATION C1 WHERE C1.COM_UUID=C.COM_UUID) ", nativeQuery = true)
    Optional<List<Communication>> findAllByFiscalYearAndComClassIdAndStatusId(Long fy, Long msgTypeIndic, Long statusId);

    //boolean existsByFiscalYearAndRfiTinAndDataIsGreaterThan(Long idFiscalYear, String rfiTin, Date date);

    @Query(value = "SELECT COM_UUID FROM COMMUNICATION WHERE PROTOCOL=:protocol GROUP BY COM_UUID", nativeQuery = true)
    String findOnlyIdByProtocol(@Param("protocol") String protocol);

    @Query(value = "SELECT * " +
            "FROM COMMUNICATION C " +
            "WHERE C.COM_UUID =:idCommunication " +
            "AND DATA=(SELECT MAX(C1.DATA) FROM COMMUNICATION C1 WHERE C1.COM_UUID=C.COM_UUID)", nativeQuery = true)
    Optional<Communication> findByComUuid(@Param("idCommunication") String idCommunication);

    @Query(value = "SELECT STATUS_ID " +
            "FROM COMMUNICATION C " +
            "WHERE C.COM_UUID IN (:idCommunications) " +
            "AND DATA=(SELECT MAX(C1.DATA) FROM COMMUNICATION C1 WHERE C1.COM_UUID=C.COM_UUID) " +
            "group by STATUS_ID", nativeQuery = true)
    Long findStatusByidCommunications(@Param("idCommunications") List<String> idCommunications);

    @Query(value = "SELECT C.STATUS_ID " +
            "FROM COMMUNICATION C " +
            "WHERE C.COM_UUID = :comUuid " +
            "AND DATA = (SELECT MAX(C1.DATA) " +
            "              FROM COMMUNICATION C1 " +
            "              WHERE C1.COM_UUID = C.COM_UUID " +
            "              and C1.STATUS_ID <> (SELECT STATUS_ID " +
            "                                     FROM COMMUNICATION C3 " +
            "                                     WHERE COM_UUID = C.COM_UUID " +
            "                                     AND DATA = (SELECT MAX(C2.DATA) " +
            "                                                   FROM COMMUNICATION C2 " +
            "                                                   WHERE C2.COM_UUID = C3.COM_UUID)))", nativeQuery = true)
    Optional<Long> findStatusBeforeDeletion(String comUuid);

    @Query(value = "SELECT MAX(C.DATA) " +
            "FROM COMMUNICATION C " +
            "WHERE C.COM_UUID = :comUuid", nativeQuery = true)
    Date getMaxDateByComUuid(String comUuid);


    @Query(value = "SELECT DISTINCT COM.COM_UUID " +
            "FROM POTENTIAL_DEST PD " +
            "INNER JOIN COUNTRY C on PD.COUNTRY_ID = C.COUNTRY_ID " +
            "INNER JOIN COMMUNICATION COM ON PD.COM_UUID = COM.COM_UUID " +
            "INNER JOIN STATUS S on COM.STATUS_ID = S.STATUS_ID " +
            "WHERE upper(C.COUNTRY_CODE) = UPPER(:cc) " +
            "AND COM.FISCAL_YEAR = :year " +
            "AND S.STATUS IN :status " +
            "AND COM.DATA = (SELECT MAX(COM2.DATA) FROM COMMUNICATION COM2 WHERE COM2.COM_UUID = COM.COM_UUID)", nativeQuery = true)
    List<String> findListCommunicationByPotentialDestCodeForYear(String cc, Long year, List<String> status);

    @Query(value = "SELECT C.COM_UUID " +
            "FROM COMMUNICATION C " +
            "WHERE C.RFI_TIN = :rfiTin " +
            "  AND C.FISCAL_YEAR = :fy " +
            "  AND C.STATUS_ID NOT IN (SELECT S.STATUS_ID FROM STATUS S WHERE S.STATUS IN :listStatus) " +
            "  AND C.DATA = (SELECT MAX(C2.DATA) FROM COMMUNICATION C2 WHERE C2.COM_UUID = C.COM_UUID) " +
            "ORDER BY C.SENT_DATE DESC", nativeQuery = true)
    List<String> findListSubsequentOrderBySentDateDesc(String rfiTin, Long fy, List<String> listStatus);

    @Query(value =
            " SELECT"
                    + " 	c2.PROTOCOL	"
                    + " FROM COMMUNICATION c2 "
                    + " WHERE "
                    + " 	C2.DATA = (SELECT MAX(C3.DATA) FROM COMMUNICATION C3 WHERE C3.COM_UUID = C2.COM_UUID)"
                    + " 	AND (c2.RFI_TIN , nvl(c2.RFI_TIN2, '-'), c2.FISCAL_YEAR) "
                    + " 		IN "
                    + " 		(SELECT "
                    + " 			c.RFI_TIN , nvl(c.RFI_TIN2, '-'), c.FISCAL_YEAR "
                    + " 		FROM "
                    + " 			COMMUNICATION C "
                    + " 		WHERE "
                    + " 			c.protocol = :protocol "
                    //+ "                          /* status CANCELED, FINALIZED shouldn't be canceled*/"
                    + " 			AND c.STATUS_ID NOT IN (3, 6)"
                    + " 		)		"
                    //+ " 	AND rownum < 2"
                    + " ORDER BY c2.PROTOCOL desc ", nativeQuery = true)
    List<String> findLastProtocolByRFIsAndFYStartingFromThisProtocol(String protocol);

    @Query(value = "SELECT * "+
    "FROM COMMUNICATION " +
    "WHERE COM_UUID IN :comUuids", nativeQuery = true)
    Optional<List<Communication>> findAllByComUuidIn(List<String> comUuids);

}
