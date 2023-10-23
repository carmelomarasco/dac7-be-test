package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
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
public class MessageTypeIndicDto implements Serializable {
	
	private static final long serialVersionUID = -121589658741222L;
	
	private String value;
	private String description;

    private MessageTypeIndicDto(MessageTypeIndic entity) {
    	this();
    	if (entity != null) {
    		this.setValue(String.valueOf(entity.getId()));
    		this.setDescription(StringUtils.trimToEmpty(entity.getValue()));
    	}
    }
    
    public static MessageTypeIndicDto getDto(MessageTypeIndic entity) {
    	if (entity != null) {
    		return new MessageTypeIndicDto(entity);
    	} else {
    		return null;
    	}
    }
    
    public static List<MessageTypeIndicDto> getDtos(List<MessageTypeIndic> ents) {
    	List<MessageTypeIndicDto> ret = new LinkedList<>();
    	if (!CollectionUtils.isEmpty(ents)) {
    		for (MessageTypeIndic fy: ents) {
    			ret.add(MessageTypeIndicDto.getDto(fy));
    		}
    	}
    	return ret;
    }

}