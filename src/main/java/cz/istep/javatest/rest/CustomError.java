package cz.istep.javatest.rest;

/* 
 * One particular Error inforamtion
 *  
 */
public class CustomError {
	private CustomErrorType errorType;
	private String message;
		
	private int entityId;
	private String entityName;
	
	
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
	
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
