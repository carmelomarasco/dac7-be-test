package it.finanze.entrate.coopint.dboard.dpi.nr.command.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Dac7NRException extends RuntimeException {

    private String message;

}
