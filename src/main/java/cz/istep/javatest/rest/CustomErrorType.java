package cz.istep.javatest.rest;

public enum CustomErrorType {	
	ID_NOT_FOUND("ID not found"), PARENT_ENTITY_NOT_FOUND("Parent entity not found");
	
	private final String descValue;
	
	private CustomErrorType(String descValue) {
		this.descValue = descValue;
	}
	
	public String getDescValue() {
		return this.descValue;
	}
}
