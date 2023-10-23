package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.ComClassEntity;
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
public class ComClassDto {
	
	private Long value;
	private String description;

    private ComClassDto(ComClassEntity entity) {
    	this();
    	if (entity != null) {
        	this.setValue(entity.getComClassId());
        	this.setDescription(StringUtils.trimToEmpty(entity.getComClassDescription()));
    	}
    }
    
    public static ComClassDto getDto(ComClassEntity entity) {
    	if (entity != null) {
    		return new ComClassDto(entity);
    	} else {
    		return null;
    	}
    }
    
    public static List<ComClassDto> getDtos(List<ComClassEntity> ents) {
    	List<ComClassDto> ret = new LinkedList<>();
    	if (!CollectionUtils.isEmpty(ents)) {
    		for (ComClassEntity fy: ents) {
    			ret.add(ComClassDto.getDto(fy));
    		}
    	}
    	return ret;
    }
}