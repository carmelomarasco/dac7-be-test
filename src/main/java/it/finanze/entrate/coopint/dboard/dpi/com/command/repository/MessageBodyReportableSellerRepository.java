package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageBodyReportableSeller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "messageBodyReportableSellerRepositoryNazionale")
public interface MessageBodyReportableSellerRepository extends JpaRepository<MessageBodyReportableSeller, Long> {
}