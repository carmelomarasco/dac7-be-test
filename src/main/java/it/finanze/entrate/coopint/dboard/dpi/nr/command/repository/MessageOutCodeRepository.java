package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value="messageOutCodeRepositoryNR")
public interface MessageOutCodeRepository extends JpaRepository<MessageOutCode, Long> {
	
	 @Query(value = "SELECT DISTINCT MSG_UUID FROM MESSAGE_OUT_CODES c  WHERE (upper(c.TIN) like upper(:poTin)  OR upper(c.IDENTIFICATION_NUMBER) like upper(:poTin))", nativeQuery = true)
	    List<String> getUuidMessageByPlatformOperatorTin(@Param("poTin") String poTin);

	 @Query(value = "SELECT DISTINCT MSG_UUID FROM MESSAGE_OUT_CODES c  WHERE upper(c.DENOMINAZIONE) like upper(:poName)", nativeQuery = true)
	    List<String> getUuidMessageByPlatformOperatorName(@Param("poName") String poName);

}