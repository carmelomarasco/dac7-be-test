package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository (value = "currencyRepositoryRes")
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByValue(String value);

}