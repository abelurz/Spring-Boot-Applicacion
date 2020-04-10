package com.abelurz.tutorial.aplicacion01.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Passgenerator {

	
	 // public static void main(String ...args) {
		  		  
		  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
	  	  //El String que mandamos al metodo encode es el password que queremos encriptar.
		  /*System.out.println(bCryptPasswordEncoder.encode("12345"));
		  //$2a$04$dKIDhWT5lRXsG5uTKl5uFO1s374Udn4tYJx3ccehsYn.tUDOp34ZO
		  //Resultado: $2a$04$n6WIRDQlIByVFi.5rtQwEOTAzpzLPzIIG/O6quaxRKY2LlIHG8uty
		   * 
		 */
	  
	  //}
	 
}
