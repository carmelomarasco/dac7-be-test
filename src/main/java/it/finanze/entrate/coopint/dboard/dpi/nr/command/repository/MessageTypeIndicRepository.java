package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository (value = "messageTypeIndicRepositoryNR")
public interface MessageTypeIndicRepository extends JpaRepository<MessageTypeIndic, Long> {

    MessageTypeIndic findByValue(String value);

    @Query(value = "SELECT * FROM MESSAGE_TYPE_INDIC T ORDER BY T.VALUE", nativeQuery = true)
    List<MessageTypeIndic> getAllOrdered();

}