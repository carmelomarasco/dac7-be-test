package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageBodyReportableSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "messageBodyReportableSellerRepositoryRes")
public interface MessageBodyReportableSellerRepository extends JpaRepository<MessageBodyReportableSeller, Long> {
}