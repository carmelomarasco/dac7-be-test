package it.finanze.entrate.coopint.dboard.dpi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class DpiCommandException extends RuntimeException {

    private String message;

}
