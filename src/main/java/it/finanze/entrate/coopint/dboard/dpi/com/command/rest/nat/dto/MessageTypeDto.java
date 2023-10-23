package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.io.Serializable;

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
public class MessageTypeDto implements Serializable {

	private static final long serialVersionUID = 18878787456954L;

	private String value;
	private String description;

}