package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="communicationRepositoryNR")
public interface CommunicationRepository extends JpaRepository<Communication, CommunicationId> {
    @Query(value = "SELECT DISTINCT MSG_UUID FROM COMMUNICATION C  WHERE UPPER(C.PLATFORM_OPERATOR_TIN) like UPPER(:poTin)", nativeQuery = true)
    List<String> getUuidMessageByPlatformOperatorTin(@Param("poTin") String poTin);

    @Query(value = "SELECT DISTINCT MSG_UUID FROM COMMUNICATION C  WHERE UPPER(C.PLATFORM_OPERATOR_NAME) like UPPER(:poName)", nativeQuery = true)
    List<String> getUuidMessageByPlatformOperatorName(@Param("poName") String poName);

    List<Communication> findCommunicationById_MsgUuidOrderByPlatformOperatorName(String msgUuid);

}