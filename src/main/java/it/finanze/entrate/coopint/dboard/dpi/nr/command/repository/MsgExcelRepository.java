package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.MsgExcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value="msgExcelRepositoryNR")
public interface MsgExcelRepository extends JpaRepository<MsgExcel, String> {

	MsgExcel findByMsgUuid(String uuid);

}