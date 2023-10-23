package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageMessageBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "messageMessageBodyRepositoryRes")
public interface MessageMessageBodyRepository extends JpaRepository<MessageMessageBody, Long> {
}