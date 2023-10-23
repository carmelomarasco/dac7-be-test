package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.io.Serializable;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryCoupleDto implements Serializable {

	private static final long serialVersionUID = -9333321212121L;
	private CountryDto receivingCountry;
    private CountryDto transmittingCountry;

    public CountryCoupleDto(Communication com) {
    	this();
    	if (com != null) {
    		this.setReceivingCountry(CountryDto.getDto(com.getReceivingCountry()));
    		this.setTransmittingCountry(CountryDto.getDto(com.getTransmittingCountry()));
    	}
    }
    
}