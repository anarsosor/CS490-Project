package HealthCare.controller;

import HealthCare.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ExceptionDTO> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(e.getMessage()), HttpStatus.FORBIDDEN);
    }
}
