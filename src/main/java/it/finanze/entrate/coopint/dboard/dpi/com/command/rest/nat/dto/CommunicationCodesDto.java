package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationCodes;
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
public class CommunicationCodesDto {
	
	private Long id;
    private String comUuid;
    private String type;
    private String tin;
    private String in;
    private String denominazione;

    private CommunicationCodesDto(CommunicationCodes entity) {
    	this();
    	if (entity != null) {
        	this.setId(entity.getId());
			this.setComUuid(entity.getComUuid());
			this.setType(entity.getType());
			this.setTin(entity.getTin());
			this.setIn(entity.getIn());
			this.setDenominazione(entity.getDenominazione());
    	}
    }
    
    public static CommunicationCodesDto getDto(CommunicationCodes entity) {
    	if (entity != null) {
    		return new CommunicationCodesDto(entity);
    	} else {
    		return null;
    	}
    }
    
    public static List<CommunicationCodesDto> getDtos(List<CommunicationCodes> ents) {
    	List<CommunicationCodesDto> ret = new LinkedList<>();
    	if (!CollectionUtils.isEmpty(ents)) {
    		for (CommunicationCodes fy: ents) {
    			ret.add(CommunicationCodesDto.getDto(fy));
    		}
    	}
    	return ret;
    }

}