package com.santander.tecnologia.controller.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.santander.tecnologia.exception.WeatherReportException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MeetupChallengeExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class,
            InvalidFormatException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {

        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgNotValidEx = (MethodArgumentNotValidException) ex;
            methodArgNotValidEx.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException methodArgTypeMismatchEx = (MethodArgumentTypeMismatchException) ex;
            String fieldName = methodArgTypeMismatchEx.getName();
            String errorMessage = String.format("should be of type %s",
                    methodArgTypeMismatchEx.getRequiredType().getName());
            errors.put(fieldName, errorMessage);
        } else {
            InvalidFormatException invalidFormatEx = (InvalidFormatException) ex.getCause();
            String pathRef = invalidFormatEx.getPathReference();
            String fieldName = pathRef.substring(pathRef.indexOf("\"") + 1, pathRef.lastIndexOf("\""));
            String errorMessage = String.format("'%s' is not a valid value for %s type", invalidFormatEx.getValue(),
                    invalidFormatEx.getTargetType());
            errors.put(fieldName, errorMessage);
        }

        return errors;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {EntityNotFoundException.class, EmptyResultDataAccessException.class,
            DataIntegrityViolationException.class, WeatherReportException.class})
    public Map<String, String> handleJpaDataExceptions(Exception ex) {

        return Collections.singletonMap("error_message", ex.getMessage());
    }

}
