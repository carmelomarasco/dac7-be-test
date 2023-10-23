package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "otherRpoAddressRepositoryNazionale")
public interface OtherRpoAddressRepository extends JpaRepository<OtherRpoAddress, Long> {
}