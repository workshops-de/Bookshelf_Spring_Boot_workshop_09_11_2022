package de.workshops.bookshelf;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BookshelfException.class)
    ResponseEntity<String> bookshelfExceptionHandler(BookshelfException e) {
        return new ResponseEntity<>("Irgendwas ist shief gelaufen: " + e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body("Falsche Eingabe");
    }
}
