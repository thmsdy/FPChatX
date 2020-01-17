package com.fpghoti.fpchatx.config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.fpghoti.fpchatx.FPChat;

public class PlayerCache {

	private FPChat plugin;
	private File configFile;
	private FileConfiguration config;

	public PlayerCache(FPChat plugin) {
		this.plugin = plugin;
		configFile = new File(this.plugin.getDataFolder(), "playercache.yml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
		config = YamlConfiguration.loadConfiguration(configFile); // In case the player had changed their name and the config updated.
	}

	public UUID getUUID(String name) {
		try {
			return UUID.fromString(config.getString(name.toLowerCase()));
		}catch(IllegalArgumentException ex) {
			return null;
		}catch(NullPointerException ex) {
			return null;
		}
	}

	public void saveUUID(String name, UUID uuid) throws IOException {
		name = name.toLowerCase();
		if (config.get(name)==null) {
			config.createSection(name);
		}
		config.set(name, uuid.toString());
		config.save(configFile);
	}



}
