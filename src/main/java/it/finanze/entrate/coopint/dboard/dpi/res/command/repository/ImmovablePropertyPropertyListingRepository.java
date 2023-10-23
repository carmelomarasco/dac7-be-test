package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.ImmovablePropertyPropertyListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "immovablePropertyPropertyListingRepositoryRes")
public interface ImmovablePropertyPropertyListingRepository extends JpaRepository<ImmovablePropertyPropertyListing, Long> {
}