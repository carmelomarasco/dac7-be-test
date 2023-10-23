package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "otherRpoAddressRepositoryNR")
public interface OtherRpoAddressRepository extends JpaRepository<OtherRpoAddress, Long> {
}