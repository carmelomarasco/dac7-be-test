package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.EffectiveDestEntity;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.EffectiveDestEntityPK;

@Repository(value = "effectiveDestRepositoryNazionale")
public interface EffectiveDestRepository extends JpaRepository<EffectiveDestEntity, EffectiveDestEntityPK> {

    @Query(value = "SELECT * " +
            "FROM EFFECTIVE_DEST ED " +
            "WHERE ED.COM_UUID = :comUuid " +
            "AND COUNTRY_ID = :countryId " +
            "AND DATA=(SELECT MAX(ED1.DATA) FROM EFFECTIVE_DEST ED1 WHERE ED.COM_UUID=ED1.COM_UUID AND ED1.COUNTRY_ID = :countryId) "
            , nativeQuery = true)
    EffectiveDestEntity findLastByComUuidAndData(@Param("comUuid") String idCommunications, @Param("countryId") Long countryId);

    @Query(value = "SELECT DISTINCT E.* " +
            "FROM EFFECTIVE_DEST E " +
            "INNER JOIN COUNTRY C ON E.COUNTRY_ID = C.COUNTRY_ID " +
            "INNER JOIN COMMUNICATION COM ON COM.COM_UUID = E.COM_UUID " +
            "INNER JOIN FISCAL_YEAR FY ON FY.YEAR = COM.FISCAL_YEAR " +
            "WHERE UPPER(C.COUNTRY_CODE) = UPPER(:cc) " +
            "AND COM.FISCAL_YEAR IN :years " +
            "AND FY.EXPIRED = 0 " +
            "AND E.DATA = (SELECT MAX(E2.DATA) FROM EFFECTIVE_DEST E2 WHERE E2.COM_UUID = E.COM_UUID AND E2.COUNTRY_ID = E.COUNTRY_ID)", nativeQuery = true)
    List<EffectiveDestEntity> getListNotExpiredByEffectiveDestCode(String cc, List<Long>years);

    @Query(value = "SELECT DISTINCT E.COM_UUID " +
            "FROM EFFECTIVE_DEST E " +
            "INNER JOIN COUNTRY C ON E.COUNTRY_ID = C.COUNTRY_ID " +
            "INNER JOIN COMMUNICATION COM ON COM.COM_UUID = E.COM_UUID " +
            "WHERE UPPER(C.COUNTRY_CODE) = UPPER(:cc) " +
            "AND COM.COM_UUID = :comUuid", nativeQuery = true)
    List<String> getComUuidForEffectiveDest(String comUuid, String cc);
}
