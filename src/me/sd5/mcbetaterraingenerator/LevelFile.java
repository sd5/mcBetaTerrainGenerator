package me.sd5.mcbetaterraingenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.IntTag;
import org.jnbt.LongTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/**
 * @author sd5
 * A representation of a Beta 1.7.3 level.dat which is needed to change the spawn and the seed.
 */
public class LevelFile {

	private File file;
	
	public long 	lastPlayed;
	public String 	levelName;
	public long 	randomSeed;
	public byte 	raining;
	public int 		rainTime;
	public long 	sizeOnDisk;
	public int 		spawnX;
	public int 		spawnY;
	public int 		spawnZ;
	public byte 	thundering;
	public int 		thunderTime;
	public long 	time;
	public int 		version;
	
	/**
	 * Loads the level.dat and reads the stored values.
	 * @param path The path to the level.dat
	 */
	public LevelFile(File path) {	
		
		try {
			
			this.file = new File(path.getAbsolutePath());

			//Create a NBT input stream.
			FileInputStream fis = new FileInputStream(file);
			GZIPInputStream gzipIs = new GZIPInputStream(fis);
			NBTInputStream nbtIs = new NBTInputStream(gzipIs);
			
			//Find through the compound clutter.
			CompoundTag rootTag = (CompoundTag) nbtIs.readTag();
			Map<String, Tag> rootMap = rootTag.getValue();	
			CompoundTag dataTag = (CompoundTag) rootMap.get("Data");
			Map<String, Tag> dataMap = dataTag.getValue();
			
			//Load the values from level.dat. Sorted like in level.dat.
			thundering 	= (Byte) 	dataMap.get("thundering").getValue();
			lastPlayed 	= (Long) 	dataMap.get("LastPlayed").getValue();
			randomSeed 	= (Long) 	dataMap.get("RandomSeed").getValue();
			version 	= (Integer) dataMap.get("version").getValue();
			time 		= (Long) 	dataMap.get("Time").getValue();
			raining 	= (Byte) 	dataMap.get("raining").getValue();
			spawnX 		= (Integer) dataMap.get("SpawnX").getValue();
			thunderTime = (Integer) dataMap.get("thunderTime").getValue();
			spawnY 		= (Integer) dataMap.get("SpawnY").getValue();
			spawnZ 		= (Integer) dataMap.get("SpawnZ").getValue();
			levelName 	= (String)	dataMap.get("LevelName").getValue();
			sizeOnDisk 	= (Long) 	dataMap.get("SizeOnDisk").getValue();
			rainTime 	= (Integer) dataMap.get("rainTime").getValue();
			
			//Close the input streams.
			nbtIs.close();
			fis.close();
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Saves the level.dat to the disk.
	 * @throws IOException
	 */
	public void save() throws IOException {
		
		//Create a NBT output stream.
		FileOutputStream fos = new FileOutputStream(file);
		GZIPOutputStream gzipOs = new GZIPOutputStream(fos);
		NBTOutputStream nbtOs = new NBTOutputStream(gzipOs);
		
		//Create new tags. Sorted like in level.dat.
		ByteTag 	tagThundering 	= new ByteTag("thundering", thundering);
		LongTag 	tagLastPlayed 	= new LongTag("LastPlayed", lastPlayed);
		LongTag 	tagRandomSeed 	= new LongTag("RandomSeed", randomSeed);
		IntTag 		tagVersion 		= new IntTag("version", version);
		LongTag 	tagTime 		= new LongTag("Time", time);
		ByteTag 	tagRaining 		= new ByteTag("raining", raining);
		IntTag 		tagSpawnX 		= new IntTag("SpawnX", spawnX);
		IntTag 		tagThunderTime 	= new IntTag("thunderTime", thunderTime);
		IntTag 		tagSpawnY 		= new IntTag("SpawnY", spawnY);
		IntTag 		tagSpawnZ 		= new IntTag("SpawnZ", spawnZ);
		StringTag 	tagLevelName 	= new StringTag("LevelName", levelName);
		LongTag 	tagSizeOnDisk 	= new LongTag("SizeOnDisk", sizeOnDisk);
		IntTag 		tagRainTime 	= new IntTag("rainTime", rainTime);
		
		Map<String, Tag> dataMap = new HashMap<String, Tag>();
		dataMap.put("thundering", tagThundering);
		dataMap.put("LastPlayed", tagLastPlayed);
		dataMap.put("RandomSeed", tagRandomSeed);
		dataMap.put("version", tagVersion);
		dataMap.put("Time", tagTime);
		dataMap.put("raining", tagRaining);
		dataMap.put("SpawnX", tagSpawnX);
		dataMap.put("thunderTime", tagThunderTime);
		dataMap.put("SpawnY", tagSpawnY);
		dataMap.put("SpawnZ", tagSpawnZ);
		dataMap.put("LevelName", tagLevelName);
		dataMap.put("SizeOnDisk", tagSizeOnDisk);
		dataMap.put("rainTime", tagRainTime);
		
		//Find through the compound clutter.
		CompoundTag dataTag = new CompoundTag("Data", dataMap);
		Map<String, Tag> rootMap = new HashMap<String, Tag>();
		rootMap.put("Data", dataTag);
		CompoundTag rootTag = new CompoundTag("", rootMap);
		
		nbtOs.writeTag(rootTag);
		
		//Flush and close the output streams.
		nbtOs.close();
		fos.close();
		
	}
	
}
