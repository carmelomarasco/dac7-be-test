package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageBody;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "messageBodyRepositoryNazionale")
public interface MessageBodyRepository extends JpaRepository<MessageBody, Long> {
}