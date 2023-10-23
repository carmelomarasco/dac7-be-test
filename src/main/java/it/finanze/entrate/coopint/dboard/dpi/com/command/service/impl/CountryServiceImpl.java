package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.DboardDpiCountry;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.DboardDpiCountryRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.CountryService;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@CommonsLog
@Data
@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    @Qualifier("dboardDpiCountryRepositoryNazionale")
    DboardDpiCountryRepository dboardDpiCountryRepository;

    public List<String> listCountryOfEffDest(String countryCodeEffDest ){
        Optional<DboardDpiCountry> effDestOptional = dboardDpiCountryRepository.findByCountryCode(countryCodeEffDest);
        if( !effDestOptional.isPresent()){
            return Lists.newArrayList();
        }
        DboardDpiCountry effDest = effDestOptional.get();
        Preconditions.checkState(Objects.equals(effDest.getCountryId(), effDest.getCountryDestinationId()), "Non è un country destinatario di comunicazioni: " + countryCodeEffDest);
        return dboardDpiCountryRepository.findByCountryId(effDestOptional.get().getCountryId()).stream()
                .map(DboardDpiCountry::getCountryCode)
                .collect(Collectors.toList());
    }

}
