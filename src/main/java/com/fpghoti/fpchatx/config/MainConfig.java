package com.fpghoti.fpchatx.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.util.Util;

public class MainConfig {

	private FPChat plugin;
	private File configFile;
	private FileConfiguration config;

	public MainConfig(FPChat plugin) {
		this.plugin = plugin;
		this.configFile = new File(plugin.getDataFolder(), "config.yml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
		generate();
	}

	private void generate(){
		if (config.get("SQL")==null) {
			config.createSection("SQL");
			config.set("SQL", false);
		}
		if (config.get("UseMariaDBDriver")==null){
			config.createSection("UseMariaDBDriver");
			config.set("UseMariaDBDriver", true);
		}
		if (config.get("Host")==null) {
			config.createSection("Host");
			config.set("Host", "0.0.0.0");
		}
		if (config.get("Port")==null) {
			config.createSection("Port");
			config.set("Port", "3306");
		}
		if (config.get("User")==null) {
			config.createSection("User");
			config.set("User", "Username");
		}
		if (config.get("Password")==null) {
			config.createSection("Password");
			config.set("Password", "blee");
		}
		if (config.get("Database")==null) {
			config.createSection("Database");
			config.set("Database", "mydb");
		}
		if (config.get("Chat-Feature-Table")==null) {
			config.createSection("Chat-Feature-Table");
			config.set("Chat-Feature-Table", "fpchat");
		}

		if (config.get("PermSync-Table")==null) {
			config.createSection("PermSync-Table");
			config.set("PermSync-Table", "fpchatsync");
		}
		if (config.get("StaffBadgeEnabled")==null) {
			config.createSection("StaffBadgeEnabled");
			config.set("StaffBadgeEnabled", false);
		}
		if (config.get("StaffBadge")==null) {
			config.createSection("StaffBadge");
			config.set("StaffBadge", "&6&l⟪Տ⟫&r");
		}
		if (config.get("ShoutCooldownEnabled")==null) {
			config.createSection("ShoutCooldownEnabled");
			config.set("ShoutCooldownEnabled", false);
		}
		if (config.get("ShoutCooldownSeconds")==null) {
			config.createSection("ShoutCooldownSeconds");
			config.set("ShoutCooldownSeconds", 45);
		}
		if (config.get("ShoutFormat")==null) {
			config.createSection("ShoutFormat");
			config.set("ShoutFormat", "&8[{prefix}&8]{suffix}{player}&8:&f ");
		}
		if (config.get("In-Message-Format")==null) {
			config.createSection("In-Message-Format");
			config.set("In-Message-Format", "&3[&a{player} &c -> &aMe&3]&f ");
		}
		if (config.get("Out-Message-Format")==null) {
			config.createSection("Out-Message-Format");
			config.set("Out-Message-Format", "&3[&aMe &c -> &a{recipient}&3]&f ");
		}
		if (config.get("Temp-Channel-Format")==null) {
			config.createSection("Temp-Channel-Format");
			config.set("Temp-Channel-Format", "&2[&6{channel}&2]&7{player}&2:&e ");
		}
		if (config.get("Filter")==null){
			config.createSection("Filter");
			config.set("Filter", false);
		}
		if (config.get("NaughtyWords")==null){
			config.createSection("NaughtyWords");
			config.set("NaughtyWords", "word1,word2,word3");
		}
		if (config.get("PrefixFilter")==null){
			config.createSection("PrefixFilter");
			config.set("PrefixFilter", false);
		}
		if (config.get("PrefixNaughtyWords")==null){
			config.createSection("PrefixNaughtyWords");
			config.set("PrefixNaughtyWords", "trusted,helper,mod,admin");
		}
		if (config.get("FrankMode")==null){
			config.createSection("FrankMode");
			config.set("FrankMode", false);
		}
		if (config.get("DistinguishedShout")==null){
			config.createSection("DistinguishedShout");
			config.set("DistinguishedShout", "&8&l(&r{prefix}&8&l)&r{suffix}{player}&8&l - &r&f");
		}
		if (config.get("DefaultChannel")==null){
			config.createSection("DefaultChannel");
			config.set("DefaultChannel", "Default");
		}
		if (config.get("PluginTag")==null){
			config.createSection("PluginTag");
			config.set("PluginTag", "&a&lFPChat&r");
		}
		if (config.get("MaxBadgeSlots")==null){
			config.createSection("MaxBadgeSlots");
			config.set("MaxBadgeSlots", "3");
		}
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isSQLEnabled() {
		return config.getBoolean("SQL");
	}
	
	public boolean useMariaDBDriver() {
		return config.getBoolean("UseMariaDBDriver");
	}

	public String getHost() {
		if(isSQLEnabled()) {
			return config.getString("Host");
		}else {
			plugin.getLogger().log(Level.SEVERE, "Null SQL host. Is MySQL disabled?");
			return null;
		}
	}

	public String getPort() {
		if(isSQLEnabled()) {
			return config.getString("Port");
		}else {
			plugin.getLogger().log(Level.SEVERE, "Null SQL port. Is MySQL disabled?");
			return null;
		}
	}

	public String getUser() {
		if(isSQLEnabled()) {
			return config.getString("User");
		}else {
			plugin.getLogger().log(Level.SEVERE, "Null SQL user. Is MySQL disabled?");
			return null;
		}
	}

	public String getPassword() {
		if(isSQLEnabled()) {
			return config.getString("Password");
		}else {
			plugin.getLogger().log(Level.SEVERE, "Null SQL password. Is MySQL disabled?");
			return null;
		}
	}

	public String getDatabase() {
		if(isSQLEnabled()) {
			return config.getString("Database");
		}else {
			plugin.getLogger().log(Level.SEVERE, "Null SQL database. Is MySQL disabled?");
			return null;
		}
	}

	public String getChatFeatureTable() {
		if(isSQLEnabled()) {
			return config.getString("Chat-Feature-Table");
		}else {
			plugin.getLogger().log(Level.SEVERE, "Null SQL chat feature table. Is MySQL disabled?");
			return null;
		}
	}

	public String getPermSyncTable() {
		if(isSQLEnabled()) {
			return config.getString("PermSync-Table");
		}else {
			plugin.getLogger().log(Level.SEVERE, "Null SQL perm sync table. Is MySQL disabled?");
			return null;
		}
	}

	public boolean staffBadgeEnabled() {
		return config.getBoolean("StaffBadgeEnabled");
	}

	public String getStaffBadge() {
		if(staffBadgeEnabled()) {
			return ChatColor.translateAlternateColorCodes('&', config.getString("StaffBadge"));
		}
		return "";
	}

	public boolean shoutCooldownEnabled(){
		return config.getBoolean("ShoutCooldownEnabled");
	}


	public int getShoutSeconds(){
		return config.getInt("ShoutCooldownSeconds");
	}

	public String getDefaultChannel() {
		return config.getString("DefaultChannel");
	}

	public String getShoutFormat() {
		return config.getString("ShoutFormat");
	}

	public String getInMsgFormat() {
		return config.getString("In-Message-Format");
	}

	public String getOutMsgFormat() {
		return config.getString("Out-Message-Format");
	}

	public String getTempChannelFormat() {
		return config.getString("Temp-Channel-Format");
	}

	public boolean chatFilterEnabled() {
		return config.getBoolean("Filter");
	}

	public String getNaughtyWords() {
		return config.getString("NaughtyWords").replace(" ", "");
	}
	
	public boolean prefixFilterEnabled() {
		return config.getBoolean("PrefixFilter");
	}

	public String getPrefixNaughtyWords() {
		return config.getString("PrefixNaughtyWords").replace(" ", "");
	}

	public boolean frankModeEnabled() {
		return config.getBoolean("FrankMode");
	}

	public String getDistinguishedShout() {
		return config.getString("DistinguishedShout");
	}
	
	public String getPluginTag() {
		return  ChatColor.translateAlternateColorCodes('&',config.getString("PluginTag"));
	}
	
	public int getMaxBadgeSlots() {
		String val = config.getString("MaxBadgeSlots");
		if(Util.isDigit(val)) {
			return Integer.parseInt(val);
		}
		return 0;
	}

}
