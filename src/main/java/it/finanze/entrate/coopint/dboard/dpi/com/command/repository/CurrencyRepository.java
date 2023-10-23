package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Currency;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository (value = "currencyRepositoryNazionale")
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByValue(String value);

}