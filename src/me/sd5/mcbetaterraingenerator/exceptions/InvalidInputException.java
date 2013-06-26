package me.sd5.mcbetaterraingenerator.exceptions;

/**
 * @author sd5
 * Throwed if there is an exception when trying to parse the user input.
 */
public class InvalidInputException extends Exception {
	
	public InvalidInputException(String message) {
		
		super(message);
		
	}
	
}
