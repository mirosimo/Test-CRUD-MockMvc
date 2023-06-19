package cz.istep.javatest.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cz.istep.javatest.exceptions.AppEntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrors> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        ValidationErrors errors = new ValidationErrors();
        List<ValidationError> errorList = result.getFieldErrors().stream().map(e -> {
            return new ValidationError(e.getField(), e.getCode());
        }).collect(Collectors.toList());
        errors.setErrors(errorList);
        return ResponseEntity.badRequest().body(errors);
    }
    
    @ExceptionHandler(AppEntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException(AppEntityNotFoundException exception) {
    	CustomError customError = new CustomError();  
    	customError.setMessage(exception.getMessage());
    	
    	return new ResponseEntity<CustomError>(customError, exception.getHttpStatus());    	
    }

}
