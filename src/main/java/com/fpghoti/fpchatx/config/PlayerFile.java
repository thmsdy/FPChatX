package com.fpghoti.fpchatx.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;

public class PlayerFile {

	private FPChat plugin;
	private File configFile;
	private FileConfiguration config;
	private String filename;
	private OfflinePlayer player;

	public PlayerFile(FPChat plugin, OfflinePlayer p) {
		this.player = p;
		filename = p.getUniqueId().toString() + ".yml";
		this.plugin = plugin;
		File pdir = new File(this.plugin.getDataFolder(), "players");
		pdir.mkdirs();
		configFile = new File(pdir, filename);
		this.config = YamlConfiguration.loadConfiguration(configFile);
		try {
			generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = YamlConfiguration.loadConfiguration(configFile); // In case the player had changed their name and the config updated.
	}

	private void generate() throws IOException{
		if (config.get("UUID")==null) {
			config.createSection("UUID");
			config.set("UUID", player.getUniqueId().toString());
		}
		if (config.get("Name")==null) {
			config.createSection("Name");
			config.set("Name", player.getName());
		}
		if (config.get("CurrentChannel")==null) {
			config.createSection("CurrentChannel");
			config.set("CurrentChannel", ChatChannel.getDefault().getName());
		}
		if (config.get("Ignore")==null) {
			config.createSection("Ignore");
			config.set("Ignore", "");
		}
		if (config.get("Channels")==null) {
			config.createSection("Channels");
			config.set("Channels", ChatChannel.getDefault().getName() + ",");
		}
		if (config.get("Spy")==null) {
			config.createSection("Spy");
			config.set("Spy", false);
		}
		if (config.get("ShoutVisible")==null) {
			config.createSection("ShoutVisible");
			config.set("ShoutVisible", true);
		}
		if (config.get("Hushed")==null) {
			config.createSection("Hushed");
			config.set("Hushed", false);
		}
		config.set("Name", player.getName());

		config.save(configFile);
	}


	public String getUUID() {
		return config.getString("UUID");
	}

	public String getName() {
		return config.getString("Name");
	}
	
	public boolean isSpy() {
		return config.getBoolean("Spy");
	}
	
	public void saveSpy(boolean spy) throws IOException {
		if (config.get("Spy")==null) {
			config.createSection("Spy");
		}
		config.set("Spy", spy);
		config.save(configFile);
	}
	
	public boolean shoutVisible() {
		return config.getBoolean("ShoutVisible");
	}
	
	public void saveShoutVisible(boolean visible) throws IOException {
		if (config.get("ShoutVisible")==null) {
			config.createSection("ShoutVisible");
		}
		config.set("ShoutVisible", visible);
		config.save(configFile);
	}
	
	public boolean isHushed() {
		return config.getBoolean("Hushed");
	}
	
	public void saveHushed(boolean hushed) throws IOException {
		if (config.get("Hushed")==null) {
			config.createSection("Hushed");
		}
		config.set("Hushed", hushed);
		config.save(configFile);
	}


	public String getCurrentChannel() {
		return config.getString("CurrentChannel");
	}

	public void saveCurrentChannel(String channel) throws IOException {
		if (config.get("CurrentChannel")==null) {
			config.createSection("CurrentChannel");
		}
		config.set("CurrentChannel", channel);
		config.save(configFile);
	}

	public String getIgnore() {
		return config.getString("Ignore");
	}

	public void saveIgnore(String ignore) throws IOException {
		if (config.get("Ignore")==null) {
			config.createSection("Ignore");
		}
		config.set("Ignore", ignore);
		config.save(configFile);
	}

	public String getChannels() {
		return config.getString("Channels");
	}

	public void saveChannels(String channels) throws IOException {
		if (config.get("Channels")==null) {
			config.createSection("Channels");
		}
		config.set("Channels", channels);
		config.save(configFile);
	}

}
