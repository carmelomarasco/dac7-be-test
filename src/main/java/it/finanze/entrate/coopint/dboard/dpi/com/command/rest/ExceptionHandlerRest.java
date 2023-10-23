package it.finanze.entrate.coopint.dboard.dpi.com.command.rest;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.entrate.coopint.dboard.dpi.common.exception.DpiCommandException;

@CommonsLog
@ControllerAdvice
@RestController
@Deprecated
public class ExceptionHandlerRest {


    @ExceptionHandler(DpiCommandException.class)
    public ResponseEntity dpiComCommandException(DpiCommandException ex) {
        log.error("Errore --> ", ex);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

}
