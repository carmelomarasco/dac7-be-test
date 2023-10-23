package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository(value="msgDataRepositoryNR")
public interface MsgDataRepository extends JpaRepository<MsgData, String> {

	MsgData findByMsgUuid(String uuid);

}