package com.fpghoti.fpchatx.config;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.fpghoti.fpchatx.FPChat;

public class ChannelFile {

	private FPChat plugin;
	private File configFile;
	private FileConfiguration config;
	private String filename;
	private String channel;

	public ChannelFile(FPChat plugin, String channel) {
		this.channel = channel;
		this.filename = channel + ".yml";
		this.plugin = plugin;
		File cdir = new File(this.plugin.getDataFolder() + File.separator + "channels");
		cdir.mkdirs();
		this.configFile = new File(cdir, filename);
		this.config = YamlConfiguration.loadConfiguration(configFile);
		try {
			generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static ArrayList<File> getChannelFiles(){
		ArrayList<File> files = new ArrayList<File>();
		File cdir = new File(FPChat.getPlugin().getDataFolder(), "channels");
		if(!cdir.exists()) {
			cdir.mkdirs();
			new ChannelFile(FPChat.getPlugin(), "Default");
		}
		File[] foundfiles = cdir.listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".yml"); 
			} 
		});
		for(File f : foundfiles) {
			files.add(f);
		}
		return files;
	}

	public static boolean channelExists(String c) {
		File cdir = new File(FPChat.getPlugin().getDataFolder() + File.separator + "channels");
		if(!cdir.exists()) {
			return false;
		}
		String fname = c + ".yml";
		File file = new File(cdir, fname);
		return file.exists();
	}

	private void generate() throws IOException{
		if (config.get("Name")==null) {
			config.createSection("Name");
			config.set("Name", channel);
		}
		if (config.get("Shortcut")==null) {
			config.createSection("Shortcut");
			config.set("Shortcut", channel);
		}
		if (config.get("ChatFormat")==null) {
			config.createSection("ChatFormat");
			config.set("ChatFormat", "&8[{prefix}&8]{suffix}{player}&8:&7 ");
		}
		if (config.get("DistinguishedChatFormat")==null){
			config.createSection("DistinguishedChatFormat");
			config.set("DistinguishedChatFormat", "&8&l(&r{prefix}&8&l)&r{suffix}{player}&8&l - &r&7");
		}
		if (config.get("Whitelist")==null) {
			config.createSection("Whitelist");
			config.set("Whitelist", false);
		}
		if (config.get("WhitelistedUUIDs")==null) {
			config.createSection("WhitelistedUUIDs");
			config.set("WhitelistedUUIDs", "");
		}
		if (config.get("WhitelistedPermissionNode")==null) {
			config.createSection("WhitelistedPermissionNode");
			config.set("WhitelistedPermissionNode", "fpchat.whitelisted." + channel);
		}
		if (config.get("HasRadius")==null) {
			config.createSection("HasRadius");
			config.set("HasRadius", false);
		}
		if (config.get("Radius")==null) {
			config.createSection("Radus");
			config.set("Radius", 300);
		}
		if (config.get("BannedUUIDs")==null) {
			config.createSection("BannedUUIDs");
			config.set("BannedUUIDs", "");
		}
		config.save(configFile);
	}


	public String getName() {
		return config.getString("Name");
	}

	public String getShortcut() {
		return config.getString("Shortcut");
	}

	public void saveShortcut(String shortcut) throws IOException{
		if (config.get("Shortcut")==null) {
			config.createSection("Shortcut");
		}
		config.set("Shortcut", shortcut);
		config.save(configFile);
	}

	public String getChatFormat() {
		return config.getString("ChatFormat");
	}

	public void saveChatFormat(String format) throws IOException{
		if (config.get("ChatFormat")==null) {
			config.createSection("ChatFormat");
		}
		config.set("ChatFormat", format);
		config.save(configFile);
	}

	public String getDistinguishedChatFormat() {
		return config.getString("DistinguishedChatFormat");
	}

	public void saveDistinguishedChatFormat(String format) throws IOException{
		if (config.get("DistinguishedChatFormat")==null) {
			config.createSection("DistinguishedChatFormat");
		}
		config.set("DistinguishedChatFormat", format);
		config.save(configFile);
	}

	public boolean isWhitelisted() {
		return config.getBoolean("Whitelist");
	}

	public void saveWhitelist(boolean whitelisted) throws IOException {
		if (config.get("Whitelist")==null) {
			config.createSection("Whitelist");
		}
		config.set("Whitelist", whitelisted);
		config.save(configFile);
	}

	public String getWhitelistedUUIDs() {
		return config.getString("WhitelistedUUIDs");
	}

	public void saveWhitelistedUUIDs(String uuids) throws IOException {
		if (config.get("WhitelistedUUIDs")==null) {
			config.createSection("WhitelistedUUIDs");
		}
		config.set("WhitelistedUUIDs", uuids);
		config.save(configFile);
	}

	public String getWhitelistedPermissionNode() {
		return config.getString("WhitelistedPermissionNode");
	}

	public void saveWhitelistedPermissionNode(String perm) throws IOException{
		if (config.get("WhitelistedPermissionNode")==null) {
			config.createSection("WhitelistedPermissionNode");
		}
		config.set("WhitelistedPermissionNode", perm);
		config.save(configFile);
	}

	public boolean hasRadius() {
		return config.getBoolean("HasRadius");
	}

	public void saveHasRadius(boolean b) throws IOException {
		if (config.get("HasRadius")==null) {
			config.createSection("HasRadius");
		}
		config.set("HasRadius", b);
		config.save(configFile);
	}

	public int getRadius() {
		return config.getInt("Radius");
	}

	public void saveRadius(int radius) throws IOException{
		if (config.get("Radius")==null) {
			config.createSection("Radius");
		}
		config.set("Radius", radius);
		config.save(configFile);
	}

	public String getBannedUUIDs() {
		return config.getString("BannedUUIDs");
	}

	public void saveBannedUUIDs(String uuids) throws IOException {
		if (config.get("BannedUUIDs")==null) {
			config.createSection("BannedUUIDs");
		}
		config.set("BannedUUIDs", uuids);
		config.save(configFile);
	}
}
