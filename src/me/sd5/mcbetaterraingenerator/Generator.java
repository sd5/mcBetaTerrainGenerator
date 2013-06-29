package me.sd5.mcbetaterraingenerator;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import me.sd5.mcbetaterraingenerator.exceptions.ServerFailureException;

/**
 * @author sd5
 * Handles the complete generation procedure.
 * Changes the spawn point in level.dat and start the generation/converting server.
 */
public class Generator {
	
	private final long seed;
	
	//This 2D-array defines the where to set the spawn points for each region.
	private static final int[][] spawnPoints = new int[][] {
		{  0 + 128,   0 + 128},
		{512 - 128,   0 + 128},
		{  0 + 128, 512 - 128},
		{512 - 128, 512 - 128}
	};
	
	/**
	 * Creates a new generator with the given seed.
	 * @param seedInput The user's input for seed.
	 */
	public Generator(String seedInput) {
		
		//Parse the seed string into a long.
		//If the input is empty, use the current time milliseconds as seed.
		//If the seed is a number, use that number as seed.
		//If the seed is not a number, user it's hashcode as seed.
		long seed;
		if(seedInput.equals("")) {
			seed = new Date().getTime();
			System.out.println("Seed: " + seed + " (random)");
		} else {
			try {
				seed = Long.parseLong(seedInput);
			} catch(NumberFormatException e) {
				seed = seedInput.hashCode();
			}
			System.out.println("Seed: " + seed);
		}
		this.seed = seed;
		
	}
	
	/**
	 * Handles the generation process.
	 * @param area The area to generate.
	 */
	public void generate(Area area, boolean printInfo) {
		
		LevelFile levelDat = new LevelFile(new File(MCBetaTerrainGenerator.genDirWorld + File.separator + MCBetaTerrainGenerator.levelDat_b173));
		MinecraftServer server = new MinecraftServer(new File(MCBetaTerrainGenerator.genDir + File.separator + MCBetaTerrainGenerator.mcserver_b173));
		
		//Change the seed in the level.dat
		levelDat.randomSeed = seed;
		try {
			levelDat.save();
		} catch (IOException e) {
			System.out.println("Could not save level.dat");
		}
		
		//Count how many regions alread generated.
		int amount = 0;
		
		//Iterate through each region.
		for(Region region : area.getRegions()) {
			//Set the spawn and run the server for each spawnpoint.
			for(int[] spawn : spawnPoints) {
				levelDat.spawnX = region.getX() * 512 + spawn[0];
				levelDat.spawnZ = region.getZ() * 512 + spawn[1];
				try {
					levelDat.save();
				} catch (IOException e) {
					System.out.println("Could not save level.dat");
				}
				
				try {
					server.run();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ServerFailureException e) {
					System.out.println(e.getMessage());
				}
				
				if(printInfo) {
					System.out.println(Util.calculatePercentage(++amount, area.getRegions().size() * spawnPoints.length));
				}
			}
			
		}
		
	}

}
