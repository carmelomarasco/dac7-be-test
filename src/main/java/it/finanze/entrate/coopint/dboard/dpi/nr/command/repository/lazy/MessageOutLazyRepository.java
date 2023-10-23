package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.lazy;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.MessageOutId;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy.MessageOutLazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value="messageOutLazyRepositoryNR")
public interface MessageOutLazyRepository extends JpaRepository<MessageOutLazy, MessageOutId>, JpaSpecificationExecutor<MessageOutLazy> {

    @Query(value = "SELECT DISTINCT * FROM MESSAGE_OUT M INNER JOIN COUNTRY QC on M.COUNTRY_ID = QC.COUNTRY_ID WHERE upper(M.BUILDING_REPORT_ID)like upper(:reportId) AND upper(QC.VALUE)=upper(:cc) ORDER BY M.BUILD_START_DATE", nativeQuery = true)
    List<MessageOutLazy> findListMessageForCountryDetail(@Param("reportId")String reportId, @Param("cc")String cc);

    @Query(value = "SELECT * " +
            "FROM MESSAGE_OUT M " +
            "INNER JOIN MSG_STATUS S on M.STATUS_ID = S.STATUS_ID " +
            "WHERE S.STATUS IN (:listStatus) " +
            "AND M.FISCAL_YEAR >= :startYear " +
            "AND M.FISCAL_YEAR <= :endYear", nativeQuery = true)
    List<MessageOutLazy> getListMsgByDateFromToAndStatus(@Param("startYear")Integer startYear, @Param("endYear")Integer endYear, @Param("listStatus")List<String> listStatus);

    @Query(value = "SELECT * " +
            "FROM MESSAGE_OUT M " +
            "INNER JOIN MSG_STATUS S on M.STATUS_ID = S.STATUS_ID " +
            "WHERE S.STATUS IN (:listStatus) ", nativeQuery = true)
    List<MessageOutLazy> getListMsgByStatus(@Param("listStatus")List<String> listStatus);

    @Query(value = "SELECT COUNT(M.MSG_UUID) " +
            "FROM MESSAGE_OUT M " +
            "INNER JOIN MSG_STATUS S on M.STATUS_ID = S.STATUS_ID " +
            "WHERE S.STATUS IN (:listStatus)", nativeQuery = true)
    Integer countMsgByStatus(@Param("listStatus")List<String> listStatus);

    @Query(value = "SELECT * " +
            "FROM MESSAGE_OUT M " +
            "WHERE M.MSG_UUID IN (SELECT SM.MSG_UUID " +
            "FROM Q_STATUS_MESSAGE SM " +
            "WHERE SM.STATUS_MESSAGE_UUID IN (SELECT DISTINCT ERR.STATUS_MESSAGE_UUID " +
            "FROM Q_VALIDATION_ERRORS ERR)) " +
            "AND M.MSG_DATE >= :startDate " +
            "AND M.MSG_DATE <= :endDate", nativeQuery = true)
    List<MessageOutLazy> getListMsgByDateFromToWithError(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<MessageOutLazy> findQMessageEntitiesByStatusIdIn(List<Short> statusId);

}