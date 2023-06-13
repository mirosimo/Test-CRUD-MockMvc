package cz.istep.javatest.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleValidationException(MethodArgumentNotValidException ex) {
    	System.out.println("********* I am in validation exception ********");
        BindingResult result = ex.getBindingResult();
        Errors errors = new Errors();
        
        List<ValidationError> errorList = result.getFieldErrors().stream().map(e -> {
            return new ValidationError(e.getField(), e.getCode());
        }).collect(Collectors.toList());
        
        errors.setErrors(errorList);
                
        return ResponseEntity.badRequest().body(errors);
    }

}
