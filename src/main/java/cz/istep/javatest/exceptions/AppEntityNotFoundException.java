package cz.istep.javatest.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;

public class AppEntityNotFoundException extends EntityNotFoundException {

	private Long entityId;
	private String message;
	
	private HttpStatus httpStatus;

	public AppEntityNotFoundException() {
		super();
	}
	
	public AppEntityNotFoundException(Long id, String message, HttpStatus httpStatus) {
		super();
		this.entityId = id;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	
	public AppEntityNotFoundException(String message) {
		this.message = message;
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
