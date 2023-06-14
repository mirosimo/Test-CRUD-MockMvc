package cz.istep.javatest.rest;

/* 
 * One particular Error inforamtion
 *  
 */
public class CustomError {
	private CustomErrorType errorType;
	private String message;
	private String stackTrace;		
	
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
	
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	


}
