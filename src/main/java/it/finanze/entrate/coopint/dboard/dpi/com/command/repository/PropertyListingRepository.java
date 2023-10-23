package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.PropertyListing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "propertyListingRepositoryNazionale")
public interface PropertyListingRepository extends JpaRepository<PropertyListing, Long> {
}