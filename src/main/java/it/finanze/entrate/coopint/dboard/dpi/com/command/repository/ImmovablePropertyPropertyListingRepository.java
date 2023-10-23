package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.ImmovablePropertyPropertyListing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "immovablePropertyPropertyListingRepositoryNazionale")
public interface ImmovablePropertyPropertyListingRepository extends JpaRepository<ImmovablePropertyPropertyListing, Long> {
}