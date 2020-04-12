package com.abelurz.tutorial.aplicacion01.Exception;

public class UsernameOrIdNotFound extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4030409573233864090L;

	public UsernameOrIdNotFound() {
		super("Usuario o ID no encontrado");
	}
	
	public UsernameOrIdNotFound(String message) {
		super(message);
	}
}
