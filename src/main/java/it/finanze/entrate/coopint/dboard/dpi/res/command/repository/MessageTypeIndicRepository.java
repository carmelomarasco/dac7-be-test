package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "messageTypeIndicRepositoryRes")
public interface MessageTypeIndicRepository extends JpaRepository<MessageTypeIndic, Long> {

    MessageTypeIndic findByValue(String value);

}