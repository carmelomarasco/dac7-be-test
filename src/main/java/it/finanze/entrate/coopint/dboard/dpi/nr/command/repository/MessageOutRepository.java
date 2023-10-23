package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="messageOutRepositoryNR")
public interface MessageOutRepository extends JpaRepository<MessageOut, MessageOutId> {
    List<MessageOut> findMessageOutByCountry_id(Long countryId);

    MessageOut findMessageOutById_msgUuid(String msgUuid);

    @Query(value = "SELECT DISTINCT MSG_UUID FROM MESSAGE_OUT M INNER JOIN  COUNTRY QC on M.COUNTRY_ID = QC.COUNTRY_ID WHERE QC.VALUE=:countryCode", nativeQuery = true)
    List<String> getUuidMessageByCountryCode(@Param("countryCode") String countryCode);

    @Query(value = "SELECT DISTINCT MSG_UUID FROM MESSAGE_OUT M INNER JOIN  COUNTRY QC on M.COUNTRY_ID = QC.COUNTRY_ID WHERE QC.VALUE IN :listCC", nativeQuery = true)
    List<String> getUuidMessageByListCountryCode(@Param("listCC")List<String> listCC);

    @Query(value = "SELECT DISTINCT * " +
            "FROM MESSAGE_OUT M " +
            "INNER JOIN  COUNTRY QC on M.COUNTRY_ID = QC.COUNTRY_ID " +
            "WHERE upper(M.BUILDING_REPORT_ID)like upper(:reportId) " +
            "AND upper(QC.VALUE)=upper(:cc) " +
            "ORDER BY M.BUILD_START_DATE", nativeQuery = true)
    List<MessageOut> findListMessageForCountryDetail(@Param("reportId")String reportId, @Param("cc")String cc);

    @Query(value = "SELECT  distinct  sum(M.PLATFORM_OPERATORS_COUNT ) FROM MESSAGE_OUT M " +
            "INNER JOIN COUNTRY QC on M.COUNTRY_ID = QC.COUNTRY_ID " +
            "WHERE upper(M.BUILDING_REPORT_ID)like upper(:reportId) " +
            "AND upper(QC.VALUE)=upper(:cc)", nativeQuery = true)
    Integer getTotalPo( @Param("reportId")String reportId,  @Param("cc")String cc);

    @Query(value = "SELECT M.MESSAGE_REF FROM MESSAGE_OUT M WHERE M.MSG_UUID =:msgUuid", nativeQuery = true)
    String getMsgRefByMsgUuid(@Param("msgUuid")String msgUuid);

}