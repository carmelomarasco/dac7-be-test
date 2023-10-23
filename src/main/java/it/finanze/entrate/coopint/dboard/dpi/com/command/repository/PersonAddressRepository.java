package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "personAddressRepositoryNazionale")
public interface PersonAddressRepository extends JpaRepository<PersonAddress, Long> {
}