package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageMessageBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "messageMessageBodyRepositoryNR")
public interface MessageMessageBodyRepository extends JpaRepository<MessageMessageBody, Long> {
}