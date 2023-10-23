package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;


import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.FiscalYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="fiscalYearRepositoryNR")
public interface FiscalYearRepository extends JpaRepository<FiscalYear, Long> {
	
	FiscalYear findByYear(Long year);
    
    List<FiscalYear> findByExpired(Boolean expired);

    @Query(value = "SELECT * FROM FISCAL_YEAR F WHERE F.YEAR IN :years", nativeQuery = true)
    List<FiscalYear> getListFromYears(List<Long> years);

    @Query(value = "SELECT * FROM FISCAL_YEAR Y ORDER BY Y.YEAR", nativeQuery = true)
    List<FiscalYear> getAllOrdered();
}
