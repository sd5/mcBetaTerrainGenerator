package me.sd5.mcbetaterraingenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import me.sd5.mcbetaterraingenerator.exceptions.ServerFailureException;

/**
 * @author sd5
 * A representation of a minecraft server.
 */
public class MinecraftServer {

	private final File serverJar;
	
	/**
	 * Creates a new minecraft server instance.
	 * @param serverJar The file of the server jar archive.
	 */
	public MinecraftServer(File serverJar) {
		
		this.serverJar = serverJar;
		
	}
	
	/**
	 * Starts the minecraft server, waits until it is ready and then stops the server again. 
	 * @throws IOException 
	 * @throws ServerFailureException 
	 */
	public void run() throws IOException, ServerFailureException {
		
		ProcessBuilder pb = new ProcessBuilder();
		pb.command("java", "-jar", serverJar.getAbsolutePath(), "nogui");
		pb.directory(serverJar.getParentFile());
		pb.redirectErrorStream(true);
		Process process;
		try {
			process = pb.start();
		} catch (IOException e) {
			throw new ServerFailureException("Failed to start the minecraft server! " + e.getMessage());
		}
		
		//A reader for reading the output of the minecraft server and writer for sending commands to the server.
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		OutputStreamWriter osw = new OutputStreamWriter(process.getOutputStream());
		
		String line = "";
		while((line = br.readLine()) != null) {
			//Wait until the server is ready then send the stop command.
			if(line.contains("[INFO] Done")) {
				osw.write("stop");
				br.close();
				osw.close();
				
				//Wait until the server process is closed.
				try {
					process.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				return;
			}
		}
		
	}
	
}
