package it.finanze.entrate.coopint.dboard.dpi.res.command.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Dac7ResException extends RuntimeException {

    private String message;

}
