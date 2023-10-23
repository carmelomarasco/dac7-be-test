package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

@Repository (value = "communicationCodesRepositoryNazionale")
public interface CommunicationCodesRepository extends JpaRepository<CommunicationCodes, Long> {

    @Query(value = "SELECT * FROM COMMUNICATION_CODES cc WHERE cc.COM_UUID = :comUuid", nativeQuery = true)
    List<CommunicationCodes> findByComUuid(@Param("comUuid")String comUuid);

    @Query(value = "SELECT * FROM COMMUNICATION_CODES cc WHERE cc.COM_UUID = :comUuid AND (cc.TIN = :cf OR cc.\"IN\" = :cf)", nativeQuery = true)
    List<CommunicationCodes> findByComUuidAndCf(@Param("comUuid")String comUuid, @Param("cf")String cf);

    @Query(value = "SELECT * " +
    "FROM COMMUNICATION_CODES cc " +
    "WHERE cc.COM_UUID = :comUuid " +
    "AND cc.DENOMINAZIONE IS NOT NULL " +
    "AND cc.DENOMINAZIONE LIKE CONCAT(:denominazione, '%')", nativeQuery = true)
    List<CommunicationCodes> findByComUuidAndDenominazione(@Param("comUuid")String comUuid, @Param("denominazione")String denominazione);
}