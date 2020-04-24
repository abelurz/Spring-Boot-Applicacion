package com.abelurz.tutorial.aplicacion01.Exception;

public class CustomeFieldValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -653965076109470962L;
	
	private String fieldName;

	public CustomeFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}

}
