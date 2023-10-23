package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageMessageBody;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "messageMessageBodyRepositoryNazionale")
public interface MessageMessageBodyRepository extends JpaRepository<MessageMessageBody, Long> {
}