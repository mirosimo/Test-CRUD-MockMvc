package cz.istep.javatest.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;

import cz.istep.javatest.rest.CustomErrorType;

public class AppEntityNotFoundException extends EntityNotFoundException {
	
	private CustomErrorType errorType;
	
	private String message;
	
	private HttpStatus httpStatus;

	public AppEntityNotFoundException() {
		super();
	}

	public AppEntityNotFoundException(String message) {
		this.message = message;
	}
	
	
	public CustomErrorType getErrorType() {
		return errorType;
	}
	public void setErrorType(CustomErrorType errorType) {
		this.errorType = errorType;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
