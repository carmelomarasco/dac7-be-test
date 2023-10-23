package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.ImmovableProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "immovablePropertyRepositoryNR")
public interface ImmovablePropertyRepository extends JpaRepository<ImmovableProperty, Long> {
}