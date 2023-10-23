package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.StatusChangeRoutes;

import java.util.Optional;

@Repository(value="statusChangeRouterRepositoryRes")
public interface StatusChangeRoutesRepository extends JpaRepository<StatusChangeRoutes, Long> {

    Optional<StatusChangeRoutes> findByRouteFrom(Long routeFrom);
}
