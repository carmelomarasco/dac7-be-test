package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.ImmovableProperty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "immovablePropertyRepositoryNazionale")
public interface ImmovablePropertyRepository extends JpaRepository<ImmovableProperty, Long> {
}