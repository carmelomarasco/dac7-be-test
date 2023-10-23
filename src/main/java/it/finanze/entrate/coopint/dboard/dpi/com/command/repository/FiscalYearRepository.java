package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.FiscalYear;

@Repository(value="fiscalYearRepositoryNazionale")
public interface FiscalYearRepository extends JpaRepository<FiscalYear, Long> {
	
	FiscalYear findByYear(Long year);
    
    List<FiscalYear> findByExpired(Boolean expired);

    @Query(value = "SELECT * FROM FISCAL_YEAR F WHERE F.YEAR IN :years", nativeQuery = true)
    List<FiscalYear> getListFromYears(List<Long> years);
}
