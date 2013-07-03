package me.sd5.mcbetaterraingenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.sd5.mcbetaterraingenerator.exceptions.ServerFailureException;

/**
 * @author sd5
 * Converts the region files to the new anvil format.
 */
public class Converter {

	//How many regions to convert with one run of the server.
	public static final int regionsPerRun = 64;
	
	/**
	 * Creates a new converter
	 */
	public Converter() {
		
		
		
	}
	
//	/**
//	 * Copies the .mcr files into the final server's folder, converts them and copies them into the finished folder.
//	 */
//	public void convert(List<Region> regions) {
//		
//		//Copy the region files.
//		for(Region region : regions) {
//			try {
//				Util.copyFile(MCBetaTerrainGenerator.genDirRegion + File.separator + region.getFilename(false), MCBetaTerrainGenerator.conDirRegion + File.separator + region.getFilename(false));
//			} catch (IOException e) {
//				System.out.println("Could not copy " + region.getFilename(false) + " to " + MCBetaTerrainGenerator.conDirRegion);
//			}
//		}
//		
//		//Start the server.
//		MinecraftServer server = new MinecraftServer(new File(MCBetaTerrainGenerator.conDir + File.separator + MCBetaTerrainGenerator.mcserver_f152));
//		try {
//			server.run();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ServerFailureException e) {
//			System.out.println(e.getMessage());
//		}
//		
//		//Copy the region files.
//		for(Region region : regions) {
//			try {
//				Util.copyFile(MCBetaTerrainGenerator.conDirRegion + File.separator + region.getFilename(true), MCBetaTerrainGenerator.endDirRegion + File.separator + region.getFilename(true));
//			} catch (IOException e) {
//				System.out.println("Could not copy " + region.getFilename(true) + " to " + MCBetaTerrainGenerator.endDir);
//				System.out.println(e.getMessage());
//			}
//		}
//		
//		//Also copy the new level.dat.
//		try {
//			Util.copyFile(MCBetaTerrainGenerator.conDirWorld + File.separator + MCBetaTerrainGenerator.levelDat_f152, MCBetaTerrainGenerator.endDirWorld + File.separator + MCBetaTerrainGenerator.levelDat_f152);
//		} catch (IOException e) {
//			System.out.println("Could not copy " + MCBetaTerrainGenerator.levelDat_f152 + " into " + MCBetaTerrainGenerator.endDirWorld);
//			System.out.println(e.getMessage());
//		}
//		
//	}
	
	/**
	 * Copies the .mcr files into the final server's folder, converts them and copies them into the finished folder.
	 */
	public void convert(List<Region> regions) {
		
		System.out.println("Converting...");
		
		//Repeat as often as needed, but at least one time.
		for(int run = 0; run < (int)Math.ceil((double)regions.size() / regionsPerRun); run++) {
			
			System.out.println((run + 1) + "/" + (int)Math.ceil((double)regions.size() / regionsPerRun));
			
			//Copy the region files.
			for(Region region : regions.subList(run * regionsPerRun, Math.min((run + 1) * regionsPerRun, regions.size()))) {
				try {
					Util.copyFile(MCBetaTerrainGenerator.genDirRegion + File.separator + region.getFilename(false), MCBetaTerrainGenerator.conDirRegion + File.separator + region.getFilename(false));
				} catch (IOException e) {
					System.out.println("Could not copy " + region.getFilename(false) + " to " + MCBetaTerrainGenerator.conDirRegion);
				}
			}
			
			//Copy the old level.dat.
			try {
				Util.copyFile(MCBetaTerrainGenerator.genDirWorld + File.separator + MCBetaTerrainGenerator.levelDat_b173, MCBetaTerrainGenerator.conDirWorld + File.separator + MCBetaTerrainGenerator.levelDat_b173);
			} catch (IOException e) {
				System.out.println("Could not copy " + MCBetaTerrainGenerator.levelDat_b173 + " into " + MCBetaTerrainGenerator.conDirWorld);
				System.out.println(e.getMessage());
			}
			
			//Start the server.
			MinecraftServer server = new MinecraftServer(new File(MCBetaTerrainGenerator.conDir + File.separator + MCBetaTerrainGenerator.mcserver_f152));
			try {
				server.run();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServerFailureException e) {
				System.out.println(e.getMessage());
			}
			
			//Copy the region files.
			for(Region region : regions.subList(run * regionsPerRun, Math.min((run + 1) * regionsPerRun, regions.size()))) {
				try {
					Util.copyFile(MCBetaTerrainGenerator.conDirRegion + File.separator + region.getFilename(true), MCBetaTerrainGenerator.endDirRegion + File.separator + region.getFilename(true));
				} catch (IOException e) {
					System.out.println("Could not copy " + region.getFilename(true) + " to " + MCBetaTerrainGenerator.endDir);
					System.out.println(e.getMessage());
				}
				
				Util.deleteFile(MCBetaTerrainGenerator.conDirRegion + File.separator + region.getFilename(false));
			}
			
			//Delete the .mcr files, because we don't want to convert them again and again.
			
		}
		
		//Also copy the new level.dat.
		try {
			Util.copyFile(MCBetaTerrainGenerator.conDirWorld + File.separator + MCBetaTerrainGenerator.levelDat_f152, MCBetaTerrainGenerator.endDirWorld + File.separator + MCBetaTerrainGenerator.levelDat_f152);
		} catch (IOException e) {
			System.out.println("Could not copy " + MCBetaTerrainGenerator.levelDat_f152 + " into " + MCBetaTerrainGenerator.endDirWorld);
			System.out.println(e.getMessage());
		}
		
	}

}
