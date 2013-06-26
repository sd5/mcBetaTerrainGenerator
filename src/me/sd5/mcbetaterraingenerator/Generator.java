package me.sd5.mcbetaterraingenerator;

import java.util.Date;

/**
 * @author sd5
 * Handles the complete generation procedure.
 * Changes the spawn point in level.dat and start the generation/converting server.
 */
public class Generator {

	long seed;
	
	/**
	 * Creates a new generator with the given seed.
	 * @param seedInput The user's input for seed.
	 */
	public Generator(String seedInput) {
		
		//Parse the seed string into a long.
		//If the input is empty, use the current time milliseconds as seed.
		//If the seed is a number, use that number as seed.
		//If the seed is not a number, user it's hashcode as seed.
		if(seedInput == "") {
			seed = new Date().getTime();
		} else {
			try {
				seed = Long.parseLong(seedInput);
			} catch(NumberFormatException e) {
				seed = seedInput.hashCode();
			}
		}
		
	}

}
