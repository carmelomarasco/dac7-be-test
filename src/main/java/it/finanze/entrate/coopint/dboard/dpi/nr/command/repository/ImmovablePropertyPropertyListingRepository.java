package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.ImmovablePropertyPropertyListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "immovablePropertyPropertyListingRepositoryNR")
public interface ImmovablePropertyPropertyListingRepository extends JpaRepository<ImmovablePropertyPropertyListing, Long> {
}