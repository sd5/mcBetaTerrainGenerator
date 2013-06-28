package me.sd5.mcbetaterraingenerator.exceptions;

/**
 * @author sd5
 * Throwed if the minecraft server process could not be started.
 */
public class ServerFailureException extends Exception {

	public ServerFailureException(String message) {
		
		super(message);
		
	}

}
