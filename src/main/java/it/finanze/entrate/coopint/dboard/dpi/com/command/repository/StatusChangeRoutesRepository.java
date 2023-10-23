package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.StatusChangeRoutes;

@Repository(value="statusChangeRouterRepositoryNazionale")
public interface StatusChangeRoutesRepository extends JpaRepository<StatusChangeRoutes, Long> {

    Optional<StatusChangeRoutes> findByRouteFrom(Long routeFrom);
}
