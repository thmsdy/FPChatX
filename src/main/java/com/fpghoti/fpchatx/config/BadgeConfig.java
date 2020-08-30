package com.fpghoti.fpchatx.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.Badge;
import com.fpghoti.fpchatx.badge.BadgeList;
import com.fpghoti.fpchatx.badge.DefaultBadges;
import com.fpghoti.fpchatx.util.Util;

public class BadgeConfig {

	private FPChat plugin;
	private File configFile;
	private FileConfiguration config;

	public BadgeConfig(FPChat plugin) {
		this.plugin = plugin;
		this.configFile = new File(this.plugin.getDataFolder(), "badges.yml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
		generate();
	}

	private void generate(){
		if (config.get("Config-Generated")==null) {
			config.options().header(""
					+ "This plugin supports up to three badges being equipped at the same time using slots 1, 2, or 3. FPChat\n"
					+ "requires MySQL to be enabled to use badges. For syncing across multiple servers, KEEP THE IDS CONSISTANT.\n"
					+ "Changing the permission or replacing a badge will NOT revoke the badge id from users who have unlocked\n"
					+ "it already in the database. When you are retiring a badge, it is recommended that you disable the badge\n"
					+ "and add a new one at the end.\n"
					+ "\n"
					+ "Usage:\n"
					+ "ID-Number:\n"
					+ "  Name: Set the name of the badge here.\n"
					+ "  Contents: This is what the badge displays as in game.\n"
					+ "  Permission: The permission for the badge. Grant in game by giving a player \"fpchat.badge.<what you put in this section>\".\n"
					+ "  Enabled: Set to false to disable the badge."
					+ "  UnlockSlot: Set to 1, 2, or 3 to give players access to the corresponding badge slot for all badges when unlocking this badge or set to -1 to not unlock a slot with the badge.\n");
			BadgeList list = DefaultBadges.getDefaultBadges();
			config.createSection("Config-Generated");
			config.set("Config-Generated", true);
			for(int i = 1; i < list.size(); i++) {
				Badge badge = list.get(i);
				String path = Integer.toString(i);
				if(config.getInt(path) == 0) {
					config.createSection(path);
				}
				if(config.getString(path + ".Name") == null) {
					config.createSection(path + ".Name");
					config.set(path + ".Name", badge.getName());
				}
				if(config.getString(path + ".Contents") == null) {
					config.createSection(path + ".Contents");
					config.set(path + ".Contents", badge.getContents().replace("§", "&"));
				}
				if(config.getString(path + ".Permission") == null) {
					config.createSection(path + ".Permission");
					config.set(path + ".Permission", badge.getRawPerm());
				}
				if(config.getString(path + ".Enabled") == null) {
					config.createSection(path + ".Enabled");
					config.set(path + ".Enabled", badge.isEnabled());
				}
				if(config.getString(path + ".UnlockSlot") == null) {
					config.createSection(path + ".UnlockSlot");
					config.set(path + ".UnlockSlot", badge.getSlotUnlock());
				}
			}
		}
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadBadges(BadgeList list) {
		for(String key : config.getKeys(false)) {
			if(Util.isDigit(key)) {
				int id = Integer.parseInt(key);
				String name = null;
				String contents = null;
				String permission = null;
				boolean enabled = false;
				int unlockSlot = 0;
				
				String item = config.getString(key + ".Name");
				if(item != null) {
					name = item;
				}

				item = config.getString(key + ".Contents");
				if(item != null) {
					contents = item;
				}
				

				item = config.getString(key + ".Permission");
				if(item != null) {
					permission = item;
				}
				
				enabled = config.getBoolean(key + ".Enabled");
				
				unlockSlot = config.getInt(key + ".UnlockSlot");
				
				Badge badge = new Badge(id, name, contents, permission, enabled, unlockSlot);
				list.add(badge);
			}
		}
	}

}
