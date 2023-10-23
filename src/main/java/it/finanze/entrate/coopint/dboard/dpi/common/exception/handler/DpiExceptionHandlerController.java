package it.finanze.entrate.coopint.dboard.dpi.common.exception.handler;

import it.finanze.entrate.coopint.dboard.dpi.common.exception.DpiCommandException;
import it.finanze.security.exception.UnauthorizedException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RestController
@ControllerAdvice
public class DpiExceptionHandlerController {

    @ExceptionHandler(value = DpiCommandException.class)
    public ResponseEntity limiteNotValid(DpiCommandException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity unauthorizedException(UnauthorizedException ex) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}
