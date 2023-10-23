package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
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
public class CountryDto {
	
    private String value;
    private String description;
    
    private CountryDto(Country entity) {
    	this();
    	if (entity != null) {
    		this.setValue(String.valueOf(entity.getId()));
    		this.setDescription(StringUtils.trimToEmpty(entity.getValue()) + " - " + StringUtils.trimToEmpty(entity.getDescription()));
    	}
    }
    
    public static CountryDto getDto(Country entity) {
    	if (entity != null) {
    		return new CountryDto(entity);
    	} else {
    		return null;
    	}
    }
    
    public static List<CountryDto> getDtos(List<Country> ents) {
    	List<CountryDto> ret = new LinkedList<>();
    	if (!CollectionUtils.isEmpty(ents)) {
    		for (Country fy: ents) {
    			ret.add(CountryDto.getDto(fy));
    		}
    	}
    	return ret;
    }

}