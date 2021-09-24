package co.com.galaxy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class HandleErrors{

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handler(ConstraintViolationException e) {
        StringBuilder strBuilder = new StringBuilder();
        e.getConstraintViolations().stream().forEach(
                violation -> {
                    strBuilder.append(violation.getMessage()+"\n");
                }
        );
        return strBuilder.toString();
    }
}
