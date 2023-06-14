package cz.istep.javatest.rest;

public enum ErrorType {	
	ID_NOT_FOUND("ID not found"), PARENT_ENTITY_NOT_FOUND("Parent entity not found");
	
	private final String descValue;
	
	private ErrorType(String val) {
		this.descValue = val;
	}
	
	public String getNiceValue() {
		return this.descValue;
	}
}
