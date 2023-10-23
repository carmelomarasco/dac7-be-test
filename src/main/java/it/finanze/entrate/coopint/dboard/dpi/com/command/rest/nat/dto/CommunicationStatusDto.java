package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationStatus;
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
public class CommunicationStatusDto {
	
	public static SimpleDateFormat onlyDateItalianFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	private Long value;
	private String description;

    private CommunicationStatusDto(CommunicationStatus entity) {
    	this();
    	if (entity != null) {
        	this.setValue(entity.getId());
        	this.setDescription(StringUtils.trimToEmpty(entity.getStatusDescription()));
    	}
    }
    
    public static CommunicationStatusDto getDto(CommunicationStatus entity) {
    	if (entity != null) {
    		return new CommunicationStatusDto(entity);
    	} else {
    		return null;
    	}
    }
    
    public static List<CommunicationStatusDto> getDtos(List<CommunicationStatus> ents) {
    	List<CommunicationStatusDto> ret = new LinkedList<>();
    	if (!CollectionUtils.isEmpty(ents)) {
    		for (CommunicationStatus fy: ents) {
    			ret.add(CommunicationStatusDto.getDto(fy));
    		}
    	}
    	return ret;
    }

}