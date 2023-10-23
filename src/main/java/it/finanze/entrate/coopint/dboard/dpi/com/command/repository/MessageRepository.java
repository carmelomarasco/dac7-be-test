package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Message;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "messageRepositoryNazionale")
public interface MessageRepository extends JpaRepository<Message, Long> {
}