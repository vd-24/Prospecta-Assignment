package com.prospecta.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        
	        // Get the binding result from the exception
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField(); // Get the field name
	            String errorMessage = error.getDefaultMessage(); // Get the error message
	            errors.put(fieldName, errorMessage); // Store in map
	        });

	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Return response with errors
	    }
	
	
	@ExceptionHandler(InvalidProductDetailsException.class)
	public ResponseEntity<MyErrorDetails> errHandler1(InvalidProductDetailsException e, WebRequest req){
		System.out.println(e.getLocalizedMessage());
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(e.getMessage());
		err.setDescription(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
}
